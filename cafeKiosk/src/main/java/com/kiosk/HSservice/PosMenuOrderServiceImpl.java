package com.kiosk.HSservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import com.kiosk.HScommand.ReceipeCmd;
import com.kiosk.HScommand.RegisterCmd;
import com.kiosk.HSdao.PosMenuOrderDao;
import com.kiosk.HSvo.CategoryVo;
import com.kiosk.HSvo.MemberVo;
import com.kiosk.HSvo.MenuVo;
import com.kiosk.HSvo.OptionListVo;
import com.kiosk.HSvo.OrderListVo;
import com.kiosk.HSvo.PaymentVo;
import com.kiosk.HSvo.ReceipeJoinVo;

@Repository
public class PosMenuOrderServiceImpl implements PosMenuOrderService{
	
	@Inject
	private PosMenuOrderDao posMenuOrderDao;
	
	@Override
	public List<CategoryVo> getCategory() {
		return posMenuOrderDao.getCategory();
	}

	@Override
	public List<MenuVo> selectMenuList(String categoryNum) {
		return posMenuOrderDao.selectMenuList(categoryNum);
	}
	
	@Override
	public List<OptionListVo> selectOptionList() {
		return posMenuOrderDao.selectOptionList();
	}
	
	@Override
	public String getMaxOrderNum(String today) {
		return posMenuOrderDao.getMaxOrderNum(today);
	}
	
	@Override
	public Map<String, MemberVo> pointCheck(String phoneNum) {
		
		MemberVo memberVo = posMenuOrderDao.memberCheck(phoneNum);
		Map<String, MemberVo> result = new HashMap<String, MemberVo>();
		
		if(memberVo != null) {
			result.put("pointCheck", memberVo);
		} else {
			result.put("pointCheck", null);
		}

		return result;
	}
	
	@Override
	public boolean insertMember(RegisterCmd registerCmd) {
		
		boolean result = false;
		
		int month = Integer.parseInt(registerCmd.getMonth());
		int day = Integer.parseInt(registerCmd.getDay());
		String birthMon = "";
		String birthDay = "";
		if(month < 10) {
			birthMon = "0" + month;
		} else {
			birthMon += month;
		}
		if(day < 10) {
			birthDay = "0" + day;
		} else {
			birthDay += day;
		}
		String birth = birthMon + birthDay;
		
		MemberVo memberVo = new MemberVo(
				registerCmd.getName(), 
				registerCmd.getPhone(), 
				registerCmd.getYear(), 
				birth);
		
		System.out.println("service에서 memberVo : " + memberVo.toString());
		
		int tmp = posMenuOrderDao.insertMember(memberVo);
		
		if(tmp > 0) {
			result = true;
		}
		
		return result;
	}
	
	@Override
	public int phoneCheck(RegisterCmd registerCmd) {
		String phoneNum = registerCmd.getPhone();
		MemberVo memberVo = posMenuOrderDao.memberCheck(phoneNum);
		
		if(memberVo != null) {
			return 1;
		}
		return 0;
		
	}
	
	@Override
	public int insertOrderList(OrderListVo orderListVo) {
		return posMenuOrderDao.insertOrderList(orderListVo);
	}
	
	@Override
	public int insertPayment(PaymentVo paymentVo) {
		return posMenuOrderDao.insertPayment(paymentVo);
	}

	@Override
	public int updateMemberPoint(MemberVo memberVo) {
		return posMenuOrderDao.updateMemberPoint(memberVo);
	}
	
	@Override
	public List<ReceipeJoinVo> receipeInfo(ReceipeCmd receipeCmd) {
		return posMenuOrderDao.receipeInfo(receipeCmd);
	}
	
	@Override
	public MemberVo memberInfo(int memberNum) {
		return posMenuOrderDao.memberInfo(memberNum);
	}
}
