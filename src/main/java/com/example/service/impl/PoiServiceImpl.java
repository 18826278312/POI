package com.example.service.impl;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.example.dto.GeocoderDto;
import com.example.dto.PlaceDto;
import com.example.service.PoiService;
import com.example.util.HttpUtil;
import com.example.util.LoadConfig;

@Service
@PropertySource(value = {"classpath:application.properties"},encoding="utf-8")
public class PoiServiceImpl implements PoiService{

	private String ak;
	
	public PoiServiceImpl() {
		ak = LoadConfig.getValue("ak");
		System.out.println(ak);
	}
	
	@Override
	public PlaceDto listPlace(String poi){
		int pageNum = 0;
		String url = "http://api.map.baidu.com/place/v2/search";
		String param = "query=" + poi + "&page_size=20&region=汕头&output=json&ak=" + ak + "&page_num=" + pageNum;
		PlaceDto placeDto = null;
		try {
			placeDto = JSONObject.parseObject(HttpUtil.sendGet(url, param), PlaceDto.class);
			if (placeDto !=null && placeDto.getStatus()==0) {
				while ((placeDto.getTotal()/20)-1 > pageNum) {
					pageNum++;
					param = "query=" + poi + "&page_size=20&region=汕头&output=json&ak=" + ak + "&page_num=" + pageNum;
					PlaceDto temp = JSONObject.parseObject(HttpUtil.sendGet(url, param),PlaceDto.class);
					placeDto.getResults().addAll(temp.getResults());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return placeDto;
	}
	
	@Override
	public String getCompleteAddress(Double lat, Double lng,String name) {
		String address = null;
		try {
			String url = "http://api.map.baidu.com/geocoder/v2/";
			String param = "location=" + lat + "," + lng + "&output=json&extensions_town=true&ak=" + ak;
			String json = HttpUtil.sendGet(url, param);
			GeocoderDto component = JSONObject.parseObject(json, GeocoderDto.class);
			if (component.getStatus()==0) {
				address = component.getResult().getAddressComponent().getCity() + component.getResult().getAddressComponent().getDistrict() + 
						component.getResult().getAddressComponent().getTown() + component.getResult().getAddressComponent().getStreet() + name;
			}else{
				address = name;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return address;
	}
	
}
