package com.sky.app.coder.helper;
 
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
 
/**
 * 实现两个实体类属性之间的复制
 * @author shamee-loop
 * 
 */
public class CopyUtils {
 
	public static void Copy(Object source, Object dest) throws Exception {
		// 获取属性
		BeanInfo sourceBean = Introspector.getBeanInfo(source.getClass(),
				java.lang.Object.class);
		PropertyDescriptor[] sourceProperty = sourceBean
				.getPropertyDescriptors();
 
		BeanInfo destBean = Introspector.getBeanInfo(dest.getClass(),
				java.lang.Object.class);
		PropertyDescriptor[] destProperty = destBean.getPropertyDescriptors();
 
		try {
			for (int i = 0; i < sourceProperty.length; i++) {
 
				for (int j = 0; j < destProperty.length; j++) {
 
					if (sourceProperty[i].getName().equals(
							destProperty[j].getName())) {
						// 调用source的getter方法和dest的setter方法
						destProperty[j].getWriteMethod().invoke(
								dest,
								sourceProperty[i].getReadMethod()
										.invoke(source));
						break;
					}
				}
			}
		} catch (Exception e) {
			throw new Exception("属性值复制失败:" + e.getMessage());
		}
	}

}
