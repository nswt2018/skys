package com.sky.app.coder.helper;

import java.util.ArrayList;
import java.util.List;

import com.sky.app.coder.model.Button;
import com.sky.app.coder.model.Form;
import com.sky.app.coder.model.Modal;
import com.sky.app.coder.model.Page1;
import com.sky.app.coder.model.Table;

public class DefaulteVueComponentPropertyValue {
	public Button getButton() {
		Button button = new Button();
		button.setSize("true");
		return button;
	}

	public Table getTable() {
		Table table = new Table();
		table.setColumns("columns");
		table.setData("data_list");
		table.setIsSelectAll("true");
		table.setHeight("410");
		table.setBorder("true");
		table.setSize("true");
		table.setStripe("true");
		table.setRef("true");
		table.setOnSelect("true");
		table.setOnSelectCancel("true");
		table.setOnSelectAll("true");
		table.setOnSelectionChange("true");
		return table;
	}
	//主从模型
	public Table getMsTable() {
		Table table = new Table();
		table.setColumns("mscolumns");
		table.setData("msdata_list");
		table.setIsSelectAll("true");
		table.setHeight("410");
		table.setBorder("true");
		table.setSize("true");
		table.setStripe("true");
		table.setRef("true");
		table.setOnSelect("true");
		table.setOnSelectCancel("true");
		table.setOnSelectAll("true");
		table.setOnSelectionChange("true");
		return table;
	}
	public Page1 getPage() {
		Page1 page = new Page1();
		page.setOnChange("true");
		page.setOnPageSizeChange("true");
		page.setShowElevator("true");
		page.setShowSizer("true");
		page.setShowTotal("true");
		page.setSize("true");
		page.setTransfer("true");
		return page;
	}

	public List<Modal> getModal() {
		Modal modal1 = new Modal();
		modal1.setWidth("700");
		modal1.setTitle("信息查看");
		modal1.setValue("viewModal");
		modal1.setForm("viewForm");
		Modal modal2 = new Modal();
		modal2.setWidth("700");
		modal2.setTitle("信息新增");
		modal2.setValue("addModal");
		modal2.setCancelText("关闭");
		modal2.setOkText("保存");
		modal2.setLoading("addloading");
		modal2.setMaskClosable("false");
		modal2.setOnOk("");
		modal2.setOnCancel("");
		modal2.setForm("addForm");
		Modal modal3 = new Modal();
		modal3.setWidth("700");
		modal3.setTitle("信息修改");
		modal3.setValue("updModal");
		modal3.setCancelText("关闭");
		modal3.setOkText("保存");
		modal3.setLoading("updloading");
		modal3.setMaskClosable("false");
		modal3.setOnOk("");
		modal3.setForm("updForm");
		List<Modal> list = new ArrayList<Modal>();
		list.add(modal1);
		list.add(modal2);
		list.add(modal3);
		return list;
	}
	//主从模型
	public List<Modal> getMsModal() {
		Modal modal1 = new Modal();
		modal1.setWidth("700");
		modal1.setTitle("信息查看");
		modal1.setValue("msviewModal");
		modal1.setForm("msviewForm");
		Modal modal2 = new Modal();
		modal2.setWidth("700");
		modal2.setTitle("信息新增");
		modal2.setValue("msaddModal");
		modal2.setCancelText("关闭");
		modal2.setOkText("保存");
		modal2.setLoading("msaddloading");
		modal2.setMaskClosable("false");
		modal2.setOnOk("");
		modal2.setOnCancel("");
		modal2.setForm("msaddForm");
		Modal modal3 = new Modal();
		modal3.setWidth("700");
		modal3.setTitle("信息修改");
		modal3.setValue("msupdModal");
		modal3.setCancelText("关闭");
		modal3.setOkText("保存");
		modal3.setLoading("msupdloading");
		modal3.setMaskClosable("false");
		modal3.setOnOk("");
		modal3.setForm("msupdForm");
		List<Modal> list = new ArrayList<Modal>();
		list.add(modal1);
		list.add(modal2);
		list.add(modal3);
		return list;
	}
	public Form[] getForm() {
		Form addform = new Form();
		addform.setRef("addFormRef");
		addform.setModel("addForm");
		addform.setRules("addRules");
		addform.setLabelWidth("100");
		addform.setInline("true");
		Form updform = new Form();
		updform.setRef("updFormRef");
		updform.setModel("updForm");
		updform.setRules("updRules");
		updform.setLabelWidth("100");
		updform.setInline("true");
		Form viewform = new Form();
		viewform.setRef("viewFormRef");
		viewform.setModel("viewForm");
		viewform.setLabelWidth("100");
		viewform.setInline("true");
		Form[] form = new Form[3];
		form[0] = addform;
		form[1] = updform;
		form[2] = viewform;
		return form;
	}
	//主从模型
	public Form[] getMsForm() {
		Form addform = new Form();
		addform.setRef("msaddFormRef");
		addform.setModel("msaddForm");
		addform.setRules("msaddRules");
		addform.setLabelWidth("100");
		addform.setInline("true");
		Form updform = new Form();
		updform.setRef("msupdFormRef");
		updform.setModel("msupdForm");
		updform.setRules("msupdRules");
		updform.setLabelWidth("100");
		updform.setInline("true");
		Form viewform = new Form();
		viewform.setRef("msviewFormRef");
		viewform.setModel("msviewForm");
		viewform.setLabelWidth("100");
		viewform.setInline("true");
		Form[] form = new Form[3];
		form[0] = addform;
		form[1] = updform;
		form[2] = viewform;
		return form;
	}
}
