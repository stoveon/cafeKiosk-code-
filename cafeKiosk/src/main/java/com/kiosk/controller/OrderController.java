package com.kiosk.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kiosk.service.IMemberService;
import com.kiosk.vo.CategoryVo;
import com.kiosk.vo.MenuVo;

@Controller
@RequestMapping(value="/cafeCarp/")
public class OrderController {
	@Autowired
	private IMemberService memberService;
	
	@RequestMapping(value="order", method=RequestMethod.GET)
	String order(HttpSession session, Model model, RedirectAttributes rttr) throws Exception {
		if(session.getAttribute("member") == null) {
			return "redirect:/cafeCarp/main";
		}
		List<CategoryVo> cateList = memberService.categoryList();
		List<MenuVo> menuList = memberService.menuList(1);
		for(MenuVo m : menuList) {
			System.out.println(m.toString());
		}
		session.setAttribute("cateList", cateList);
		model.addAttribute("menuList", menuList);
		return "user/orderForm";
	}
	
	@RequestMapping(value="orderSet/{num}")
	String orderSet(@PathParam(value="num") int num, HttpSession session, Model model) throws Exception {
		List<MenuVo> menuList = memberService.menuList(num);
		model.addAttribute("menuList", menuList);
		return "user/orderForm";
	}
}