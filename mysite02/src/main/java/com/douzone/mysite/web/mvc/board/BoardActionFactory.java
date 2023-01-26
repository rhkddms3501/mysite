package com.douzone.mysite.web.mvc.board;

import com.douzone.web.mvc.Action;
import com.douzone.web.mvc.ActionFactory;

public class BoardActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		
		if("writeform".equals(actionName)) {
			action = new WriteformAction();
		} else if("write".equals(actionName)) {
			action = new WriteAction();
		} else if("delete".equals(actionName)) {
			action = new DeleteAction();
		} else if("view".equals(actionName)) {
			action = new ViewAction();
		} else if("modifyform".equals(actionName)) {
			action = new ModifyformAction();
		} else if("modify".equals(actionName)) {
			action = new ModifyAction();
		} else if("replyform".equals(actionName)) {
			action = new ReplyformAction();
		} else if("reply".equals(actionName)) {
			action = new ReplyAction();
		} else {
			action = new ListAction();
		}
		
		return action;
	}
}