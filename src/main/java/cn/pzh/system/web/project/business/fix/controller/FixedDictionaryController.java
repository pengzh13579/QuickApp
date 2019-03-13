package cn.pzh.system.web.project.business.fix.controller;

import cn.pzh.system.web.project.common.constant.KeyConstants;
import cn.pzh.system.web.project.common.model.AjaxJson;
import cn.pzh.system.web.project.dao.first.entity.fix.FixedDictionaryEntity;
import cn.pzh.system.web.project.business.fix.service.DictionaryService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping ("/fixedDictionaryController")
public class FixedDictionaryController {

    @Autowired
    private DictionaryService dictionaryService;

    /***
     * 字典列表页面
     * @return 字典列表页面
     */
    @RequestMapping ("/list")
    public String list() {
        return "/fix/dictionary/dictionary_list";
    }

    /***
     * 字典添加页面
     * @param pid 父字典ID
     * @param model 模型
     * @return 字典添加页面
     */
    @RequestMapping ("/add/{pid}")
    public String add(@PathVariable Integer pid, Model model) {

        // 父节点ID
        model.addAttribute("pid", pid);

        // 子节点的话获取父节点名
        if (pid != 0) {
            model.addAttribute("pidName", dictionaryService.get(pid)
                    .getDictionaryName());
        }

        // 前台传入的父节点ID为0，则为父节点
        model.addAttribute("isParentFlag", pid == 0);
        return "/fix/dictionary/dictionary_form";
    }

    /***
     * 字典修改页面
     * @param id 字典ID
     * @param model 模型
     * @return 字典修改页面
     */
    @RequestMapping ("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {

        FixedDictionaryEntity fixedDictionaryEntity = dictionaryService.get(id);

        // 父节点ID
        model.addAttribute("pid", fixedDictionaryEntity.getPid());

        // 是否为父节点，如果父节点ID为0的话则为父节点
        model.addAttribute("isParentFlag", fixedDictionaryEntity.getPid() == 0);

        // 子节点的话获取父节点名
        if (fixedDictionaryEntity.getPid() != 0) {
            model.addAttribute("pidName", dictionaryService.get(fixedDictionaryEntity.getPid())
                    .getDictionaryName());
        }

        // 字典信息
        model.addAttribute("info", fixedDictionaryEntity);
        return "/fix/dictionary/dictionary_form";
    }

    /***
     * 字典信息页面
     * @param id 字典ID
     * @param model 模型
     * @return 字典信息页面
     */
    @RequestMapping ("/info/{id}")
    public String info(@PathVariable Integer id, Model model) {

        FixedDictionaryEntity fixedDictionaryEntity = dictionaryService.get(id);

        // 父节点ID
        model.addAttribute("pid", fixedDictionaryEntity.getPid());

        // 子节点的话获取父节点名
        if (fixedDictionaryEntity.getPid() != 0) {
            model.addAttribute("pidName", dictionaryService.get(fixedDictionaryEntity.getPid())
                    .getDictionaryName());
        }

        // 是否为父节点，如果父节点ID为0的话则为父节点
        model.addAttribute("isParentFlag", fixedDictionaryEntity.getPid() == 0);

        // 字典信息
        model.addAttribute("info", fixedDictionaryEntity);
        return "/fix/dictionary/dictionary_read_form";
    }

    /***
     * 根据字典编码查询数据字典集合
     * @param dictionaryCode 字典编码
     * @return 数据字典集合
     */
    @RequestMapping ("/getDictionarys/{dictionaryCode}")
    @ResponseBody
    public String getDictionarys(@PathVariable String dictionaryCode) {

        FixedDictionaryEntity fixedDictionaryEntity = dictionaryService.get(dictionaryCode);
        if (null == fixedDictionaryEntity) {
            return null;
        }

        // 根据查询实体类得到列表
        List<FixedDictionaryEntity> list = dictionaryService.getDictionarys(fixedDictionaryEntity.getId());

        // JSONObject
        JSONObject result = new JSONObject();

        // rows存放每页记录 ，这里的两个参数名是固定的，必须为 total和 rows
        result.put(KeyConstants.DICTIONARY_RETURN_HAS_EMPTY, fixedDictionaryEntity.getHasEmpty());

        // rows存放每页记录 ，这里的两个参数名是固定的，必须为 total和 rows
        result.put(KeyConstants.DICTIONARY_RETURN_DATA_LIST, list);
        return result.toJSONString();
    }

    /***
     * 字典列表信息查询
     * @param fixedDictionaryEntity 查询实体类
     * @return 字典列表信息
     */
    @RequestMapping ("/listDictionarys")
    @ResponseBody
    public String listDictionarys(FixedDictionaryEntity fixedDictionaryEntity) {

        // 根据查询实体类得到列表
        List<FixedDictionaryEntity> list = dictionaryService.listDictionarys(fixedDictionaryEntity);

        // 将列表信息绑定到pageInfo
        PageInfo<FixedDictionaryEntity> page = new PageInfo<>(list);

        // JSONObject
        JSONObject result = new JSONObject();

        // total 存放总记录数
        result.put(KeyConstants.PAGE_RETURN_TOTAL, page.getTotal());

        // rows存放每页记录 ，这里的两个参数名是固定的，必须为 total和 rows
        result.put(KeyConstants.PAGE_RETURN_ROWS, list);
        System.out.println(result.toJSONString());
        return result.toJSONString();
    }

    /***
     * 添加字典信息
     * @param info 页面字典信息
     * @return 字典添加结果
     */
    @RequestMapping (value = "/addDictionary")
    @ResponseBody
    public AjaxJson addEntity(FixedDictionaryEntity info) {

        AjaxJson j = new AjaxJson();

        // 插入字典
        if (dictionaryService.insert(info) > 0) {
            j.setMsg("字典添加成功！");
            j.setSuccess(true);
            return j;
        }

        j.setSuccess(false);
        j.setMsg("字典添加失败，请联系管理员！");
        return j;
    }

    /***
     * 修改字典信息
     * @param info 页面字典信息
     * @return 字典修改结果
     */
    @RequestMapping (value = "/editDictionary")
    @ResponseBody
    public AjaxJson editEntity(FixedDictionaryEntity info) {

        AjaxJson j = new AjaxJson();

        // 更新字典
        if (dictionaryService.update(info) > 0) {
            j.setMsg("字典修改成功");
            j.setSuccess(true);
            return j;
        }
        j.setMsg("字典修改失败，请联系管理员！");
        j.setSuccess(false);
        return j;
    }

    /***
     * 逻辑删除字典--将disFlag变为1
     * @param id 字典ID
     * @return 字典删除结果
     */
    @RequestMapping ("/delete")
    public AjaxJson delete(Integer id) {

        AjaxJson j = new AjaxJson();

        // 逻辑删除字典
        if (dictionaryService.delete(id) > 0) {
            j.setMsg("字典删除成功");
            j.setSuccess(true);
            return j;
        }

        j.setSuccess(false);
        j.setMsg("字典删除失败，请联系管理员！");
        return j;
    }

}
