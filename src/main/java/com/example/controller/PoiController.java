package com.example.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.dto.PlaceDto;
import com.example.dto.PlaceResult;
import com.example.service.PoiService;
import com.example.util.FileUtil;
import com.example.util.LoadConfig;

@Controller
@RequestMapping("/PoiController")
public class PoiController {

	@Resource
	private PoiService poiService;
	
	private String fileUrl;
	
	public PoiController() {
		fileUrl = LoadConfig.getValue("file.url");
	}

	@RequestMapping(value="listPlace")
	@ResponseBody
	public Map<String, Object> listPlace(String poi){
		Map<String, Object> map = new HashMap<String,Object>();
		List<String> list = new ArrayList<String>();
		try {
			PlaceDto placeDto = poiService.listPlace(poi);
			if (placeDto.getStatus()==0) {
				int i=1;
				for(PlaceResult place : placeDto.getResults()){
					System.out.println(i++);
					list.add(poiService.getCompleteAddress(place.getLocation().getLat(), place.getLocation().getLng(),place.getName()) + "    " + 
							place.getLocation().getLat() + "    " + place.getLocation().getLng());
				}
				FileUtil.writeTxtFile(list, fileUrl + poi + ".txt");
				map.put("status", 0);
			}else{
				map.put("status", 1);
				map.put("status", placeDto.getMessage());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("status", 5);
			map.put("info", e.toString());
		}
		return map;
	}
}
