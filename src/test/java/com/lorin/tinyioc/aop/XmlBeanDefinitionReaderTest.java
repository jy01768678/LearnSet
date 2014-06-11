package com.lorin.tinyioc.aop;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.lorin.tinyioc.beans.BeanDefinition;
import com.lorin.tinyioc.beans.io.ResourceLoader;
import com.lorin.tinyioc.beans.xml.XmlBeanDefinitionReader;

/**
 */
public class XmlBeanDefinitionReaderTest {

	@Test
	public void test() throws Exception {
		XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new ResourceLoader());
		xmlBeanDefinitionReader.loadBeanDefinitions("tinyioc.xml");
		Map<String, BeanDefinition> registry = xmlBeanDefinitionReader.getRegistry();
		Assert.assertTrue(registry.size() > 0);
	}
}
