package nuggets.demo.Controller;

import lombok.RequiredArgsConstructor;
import nuggets.demo.Service.ServiceImpl.IndexServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class IndexController {

    private final IndexServiceImpl indexService;

    @GetMapping("/index.html")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("products", indexService.findAll());
        mav.addObject("largeProducts", indexService.getLargeProducts());
        mav.addObject("smallProducts", indexService.getSmallProducts());
        return mav;
    }

}
