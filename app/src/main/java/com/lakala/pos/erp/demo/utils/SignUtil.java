package com.lakala.pos.erp.demo.utils;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.lakala.pos.erp.demo.utils.base.RsaUtil;
import com.lakala.pos.erp.demo.utils.base.base64.Base64Utils;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.*;

public class SignUtil {

	public static String signWithPrivateKey(String linkStr, String prvKey) {
		PrivateKey privateKey = RsaUtil.getPrivateKey(prvKey);
		byte []signBytes = RsaUtil.signWithPrivateKey(privateKey, linkStr.getBytes(StandardCharsets.UTF_8), "SHA256withRSA");
		assert signBytes != null;
		return new String(Base64Utils.encode(signBytes));
	}

	public static Boolean verifyByPublicKey(String linkStr, String sign, String pubKey) {
		return RsaUtil.verifyByPublicKey(RsaUtil.getPublicKey(pubKey),
				linkStr.getBytes(StandardCharsets.UTF_8), Base64Utils.decode(sign), "SHA256withRSA");
	}

	public static Map<String, Object> getAllParamsByJson(String jsonStr) {
		JSONObject jsonObject = JSONObject.parseObject(jsonStr, Feature.OrderedField);
		JSONObject header = jsonObject.getJSONObject("header");
		JSONObject body = jsonObject.getJSONObject("body");
		if (BaseUtil.isNotEmpty(body)) {
			for (String key : body.keySet()) {
				header.put(key, body.get(key));
			}
		}
		Map<String, Object> map = new TreeMap<>();
		for (String key : header.keySet()) {
			Object value = header.get(key);
			if (BaseUtil.isNotEmpty(value)) {
				map.put(key, value);
			}
		}
		return map;

	}

	public static Map<String, Object> getAllParams(Object obj) {
		// 请求参数为下划线格式
		String JsonStr = JSONObject.toJSONString(obj);
		return getAllParamsByJson(JsonStr);
	}

	/**
	 * 待签字符串生成
	 *
	 * @param params 需要排序并参与字符拼接的参数组
	 * {
	 *     "body":{
	 *         "C":"textC",
	 *         "A":{
	 *             "c":"textC",
	 *             "b":"textB",
	 *             "a":"textA"
	 *         }
	 *     },
	 *     "header":{
	 *         "B":"textB",
	 *         "D":"textD",
	 *         "E":""
	 *     }
	 * }
	 * @return 拼接后字符串 A={"c":"textC","b":"textB","a":"textA"}&B=textB&C=textC&D=textD
	 */
	public static String createLinkString(Map<String, Object> params) {

		List<String> keys = new ArrayList<>(params.keySet());
		Collections.sort(keys);
		StringBuilder linkStr = new StringBuilder();
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			Object value = params.get(key);
			if (BaseUtil.isEmpty(value)) {
				continue;
			}
			if (i == keys.size() - 1) {
				linkStr.append(key).append("=").append(value);
			} else {
				linkStr.append(key).append("=").append(value).append("&");
			}
		}

		return linkStr.toString();
	}
}
