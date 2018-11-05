package com.sky.app.coder.service;

import java.util.List;

import com.sky.app.coder.model.Element;

public interface ICoderService{
	List<Element> getTagInfo(String sqlId,String moduCode);
	Element getElement(String sqlId,String moduCode);
}
