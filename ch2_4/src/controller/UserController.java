package controller;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import pojo.User;
import service.UserService;
@Controller
@RequestMapping("/user")
public class UserController {
	// 得到一个用来记录日志的对象，这样打印信息的时候能够标记打印的是那个类的信息
	private static final Log logger = LogFactory.getLog(UserController.class);
	@Autowired
	private UserService userService;
	@RequestMapping(value = "/input")   //对应index.jsp页面请求中的"user/input"
	public String inputUser(Model model) {
		HashMap<String, String> hobbys = new HashMap<String, String>();
		hobbys.put("篮球", "篮球");
		hobbys.put("乒乓球", "乒乓球");
		hobbys.put("电玩", "电玩");
		hobbys.put("游泳", "游泳");
	     // 如果model中没有user属性，userAdd.jsp会抛出异常，因为表单标签无法找到
// modelAttribute属性指定的form backing object
		model.addAttribute("user", new User());
		model.addAttribute("hobbys", hobbys);
	model.addAttribute("carrers", new String[] { "教师", "学生", "coding搬运工", "IT民工", "其它" });
		model.addAttribute("houseRegisters", new String[] { "北京", "上海", "广州", "深圳", "其它" });
		return "userAdd";   //返回视图View的userAdd.jsp
	}
	@RequestMapping(value = "/save")    //对应userAdd.jsp页面表单action中的"user/save"
	public String addUser(@ModelAttribute User user, Model model) {
		if (userService.addUser(user)) {
			logger.info("成功");
			return "redirect:/user/list";
		} else {
			logger.info("失败");
			HashMap<String, String> hobbys = new HashMap<String, String>();
			hobbys.put("篮球", "篮球");
			hobbys.put("乒乓球", "乒乓球");
			hobbys.put("电玩", "电玩");
			hobbys.put("游泳", "游泳");
			// 这里不需要model.addAttribute("user", new
			// User())，因为@ModelAttribute指定form backing object
			model.addAttribute("hobbys", hobbys);
	model.addAttribute("carrers", new String[] { "教师", "学生", "coding搬运工", "IT民工", "其它" });
		model.addAttribute("houseRegisters", new String[] { "北京", "上海", "广州", "深圳", "其它" });
			return "userAdd";     //返回视图View的userAdd.jsp
		}
	}
	@RequestMapping(value = "/list")    //对应index.jsp页面请求中的"user/list"
	public String listUsers(Model model) {
		List<User> users = userService.getUsers();
		model.addAttribute("users", users);
		return "userList";    //返回视图View的userList.jsp
	}
}
