package com.kiosk.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kiosk.command.MenuOrderCommand;
import com.kiosk.service.IMemberService;
import com.kiosk.vo.CategoryVo;

@Controller
@RequestMapping(value="/cafeCarp/")
public class OrderController {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	//logger.info();		sysout 대신
	
	@Autowired		//	@Inject
	private IMemberService memberService;
	
	@RequestMapping(value="order", method=RequestMethod.GET)
	String order(@RequestParam(value="num", defaultValue="1") int num, HttpSession session, Model model) throws Exception {
		if(session.getAttribute("cateList") == null) {
			List<CategoryVo> cateList = memberService.categoryList();
			session.setAttribute("cateList", cateList);			
		}
		List<HashMap<String, String>> menuList = memberService.menuList(num);
		model.addAttribute("menuList", menuList);
		if(session.getAttribute("pageNum") != null) {
			session.removeAttribute("pageNum");
		}
		session.setAttribute("pageNum", num);
		return "kiosk/orderForm";
	}
	
	@RequestMapping(value="orderSet", method=RequestMethod.POST)
	public String orderSet(MenuOrderCommand moc, HttpSession session, Model model, RedirectAttributes rttr) {
		List<MenuOrderCommand> orderList = new ArrayList<>();
		int toNum = moc.getPrice();
		if(session.getAttribute("orderList") != null) {
			orderList = (List<MenuOrderCommand>) session.getAttribute("orderList");	
			toNum += (Integer) session.getAttribute("orderTotal");	
			session.removeAttribute("orderList");
			session.removeAttribute("orderCount");
			session.removeAttribute("orderTotal");
		}
		orderList.add(moc);
		if(session.getAttribute("pageNum") != null) {
			session.removeAttribute("pageNum");
		}
		session.setAttribute("pageNum", moc.getCategoryNum());
		session.setAttribute("orderList", orderList);
		session.setAttribute("orderCount", orderList.size());
		session.setAttribute("orderTotal", toNum);
		return "redirect:/cafeCarp/order?num="+moc.getCategoryNum();
	}
	
	@RequestMapping(value="orderDel")
	public String orderSet(@RequestParam(value="num") int num, HttpSession session, Model model) {
		List<MenuOrderCommand> orderList = (List<MenuOrderCommand>) session.getAttribute("orderList");
		int toNum = (Integer) session.getAttribute("orderTotal");
		toNum -= orderList.get(num).getPrice();
		orderList.remove(num);
		session.removeAttribute("orderList");
		session.removeAttribute("orderTotal");
		session.setAttribute("orderList", orderList);
		session.setAttribute("orderTotal", toNum);
		int pageNum = (Integer) session.getAttribute("pageNum");
		return "redirect:/cafeCarp/order?num="+pageNum ;
	}
	
	@RequestMapping(value="orderResult")
	public String orderSet() {
		return "kiosk/orderResultForm";
	}
	
	@RequestMapping(value="scroll")
	public String scroll(@RequestParam(value="type") String type, HttpSession session, RedirectAttributes rttr) throws Exception {
		int page =(Integer) session.getAttribute("pageNum");
		int cateLen = memberService.categoryList().size();
		if(type.equals("N") || type == "N") { 
			if(page < cateLen) {
				page += 1; 	
				rttr.addFlashAttribute("scDis", "R");
			}
		}
		if(type.equals("A") || type == "A") {
			if(1 < page) {
				page -=1; 
				rttr.addFlashAttribute("scDis", "L");
			}
		}
		return "redirect:/cafeCarp/order?num="+page;
	}
	
}
