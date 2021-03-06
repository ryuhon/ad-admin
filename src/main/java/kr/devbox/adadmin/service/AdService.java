package kr.devbox.adadmin.service;

import kr.devbox.adadmin.dto.AdParamDTO;
import kr.devbox.adadmin.dto.ListParamDTO;
import kr.devbox.adadmin.entity.Ad;
import kr.devbox.adadmin.entity.ListParam;
import kr.devbox.adadmin.mapper.AdMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class AdService {


    private final AdMapper adMapper;
    private final ModelMapper modelMapper;

    public AdService(AdMapper adMapper, ModelMapper modelMapper) {
        this.adMapper = adMapper;
        this.modelMapper = modelMapper;
    }



    public Boolean create(AdParamDTO dtoParam) {

        Ad param = modelMapper.map(dtoParam,Ad.class);
        Boolean result =  adMapper.adCreate(param);
        return result ;
    }

    public Integer getCount(ListParamDTO dtoParam) {
        ListParam param = modelMapper.map(dtoParam, ListParam.class);
        return adMapper.adCount(param);
    }

    public AdParamDTO getOne(String aid) {
        Ad ad =  adMapper.adGetOne(aid);
        AdParamDTO result = modelMapper.map(ad,AdParamDTO.class);
        return result;
    }

    public List<AdParamDTO> getList(ListParamDTO dtoParam) {
        ListParam param = modelMapper.map(dtoParam, ListParam.class);
        List<Ad> resultEntity = adMapper.adList(param);

        Type listType = new TypeToken<List<AdParamDTO>>(){}.getType();
        List<AdParamDTO> result = modelMapper.map(resultEntity,listType);
        return result;
    }

    public Boolean delete(String aid) {
        return adMapper.adDelete(aid);
    }

    public Boolean update(AdParamDTO dtoParam) {
        Ad param = modelMapper.map(dtoParam,Ad.class);
        Boolean result =  adMapper.adCreate(param);
        return result ;
    }
}
