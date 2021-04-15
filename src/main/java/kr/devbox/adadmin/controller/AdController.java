package kr.devbox.adadmin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.devbox.adadmin.dto.AdParamDTO;
import kr.devbox.adadmin.dto.ListParamDTO;
import kr.devbox.adadmin.dto.ReportDTO;
import kr.devbox.adadmin.dto.RestDTO;
import kr.devbox.adadmin.service.AdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Api(tags = {"2. Ad"})
@RestController
@RequestMapping(path = "/api")
public class AdController {

    final AdService adService;
    public AdController(AdService adService){
        this.adService = adService;
    }

    @ApiOperation(value = "광고 리스트", notes = "광고 리스트")
    @RequestMapping(value="/ads", method= RequestMethod.GET)
    @ResponseBody
    public Object adList( ListParamDTO param) {
//        log.debug("param : " + param.toString());
        List<AdParamDTO> adList =  adService.getList(param);

        int total = adService.getCount(param);
        return new RestDTO(adList,total);
    }
    @ApiOperation(value = "광고 조회", notes = "광고 조회")
    @RequestMapping(value="/ads/{aid}", method= RequestMethod.GET)
    public Object adGetOne(@PathVariable("aid") String aid) {
        AdParamDTO ad =  adService.getOne(aid);
        return new RestDTO(ad,"");
    }

    @ApiOperation(value = "광고 추가", notes = "광고 추가")
    @RequestMapping(value="/ads", method= RequestMethod.POST)
    public Object adCreate(@RequestBody @Valid AdParamDTO ad) {
        return new RestDTO(adService.create(ad),"광고를 생성했습니다.");

    }

    @ApiOperation(value = "광고 수정", notes = "광고 수정")
    @RequestMapping(value="/ads/{aid}", method= RequestMethod.PUT)
    public Object  adUpdate(@PathVariable("aid") Integer aid,@RequestBody @Valid AdParamDTO ad) {


        return new RestDTO(adService.update(ad), "광고를 수정했습니다.");

    }
    @ApiOperation(value = "광고 삭제", notes = "광고 삭제")
    @RequestMapping(value="/ads/{aid}", method= RequestMethod.DELETE)
    public Object adDelete(@PathVariable("aid") String aid) throws Exception {

        AdParamDTO quiz =  adService.getOne(aid);
        return new RestDTO(adService.delete(aid), "광고를 삭제했습니다.");

    }
    @ApiOperation(value = "광고 통계", notes = "광고 통계")
    @RequestMapping(value="/report", method= RequestMethod.GET)
    public Object adDelete() throws Exception {

        List<ReportDTO> report =  adService.report();
        return new RestDTO(report, report.size());

    }
}
