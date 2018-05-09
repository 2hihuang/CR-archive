package com.cr.archive;

import com.cr.archive.cardtype.Common;
import com.cr.archive.cardtype.Epic;
import com.cr.archive.cardtype.Legendary;
import com.cr.archive.cardtype.Rare;
import com.cr.archive.model.Profile;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@EnableAutoConfiguration
public class DemoApplication {


	@RequestMapping(value = "/profile", produces = "text/plain;charset=UTF-8")
	//@ResponseBody
	ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView("index");
		//Profile p = new Profile(tag);
		Profile p1 = new Profile("82UYLC9J");
		Profile p2 = new Profile("2vpjvclc");
		String text = "Hello 无尽星空" + "<br/>";
		int i = 0;
		for (Map.Entry<String, String> entry: p1.getChestsQueue().entrySet()){
			text += entry.getKey() + "==" + entry.getValue() + "<br/>";
			i++;
		}
		while ( i < 15 ) {
			text += "<br/>";
			i++;
		}
		text += addCardInfo(p1);
		modelAndView.addObject("p1", text);
		text = "Hello 暗影奇袭" + "<br/>";
		i = 0;
		for (Map.Entry<String, String> entry: p2.getChestsQueue().entrySet()){
			text += entry.getKey() + "==" + entry.getValue() + "<br/>";
			i++;
		}
		while ( i < 15 ) {
			text += "<br/>";
			i++;
		}
		text += addCardInfo(p2);
		modelAndView.addObject("p2", text);
		return modelAndView;
	}

	private String addCardInfo(Profile p) {
		int common = 0;
		int rare =0;
		int epic = 0;
		int legendary = 0;
		int commonCurNum = 0;
		int commonCurExp = 0;
		int commonCurGold = 0;
		int commonRemainNum = 0;
		int commonRemainExp = 0;
		int commonRemainGold = 0;
		int rareCurNum = 0;
		int rareCurExp = 0;
		int rareCurGold = 0;
		int rareRemainNum = 0;
		int rareRemainExp = 0;
		int rareRemainGold = 0;
		int epicCurNum = 0;
		int epicCurExp = 0;
		int epicCurGold = 0;
		int epicRemainNum = 0;
		int epicRemainExp = 0;
		int epicRemainGold = 0;
		int legCurNum = 0;
		int legCurExp = 0;
		int legCurGold = 0;
		int legRemainNum = 0;
		int legRemainExp = 0;
		int legRemainGold = 0;
		String comCardsInfo = "";
		String rareCardsinfo = "";
		String epicCardsInfo = "";
		String legCardsInfo = "";

		for (Object o : p.getCardSet()) {
			if (o instanceof Common){
				Common card = (Common) o;
				common++;
				commonCurNum += card.getCurrentGross();
				commonCurGold += card.getCurrentGold();
				commonCurExp += card.getCurrentExp();
				commonRemainNum += card.getRemainGross();
				commonRemainGold += card.getRemainGold();
				commonRemainExp += card.getRemainExp();
				comCardsInfo += "<img src=\"https://statsroyale.com/images/cards/full/" +card.imgUrl + "\" height=\"30\" width=\"25\">" + "Level:" + card.level + ", Number:" + card.getCurrentGross() + "<br/>";
			}else if (o instanceof Rare){
				Rare card = (Rare) o;
					rare++;
				rareCurNum += card.getCurrentGross();
				rareCurGold += card.getCurrentGold();
				rareCurExp += card.getCurrentExp();
				rareRemainNum += card.getRemainGross();
				rareRemainGold += card.getRemainGold();
				rareRemainExp += card.getRemainExp();
				rareCardsinfo += "<img src=\"https://statsroyale.com/images/cards/full/" +card.imgUrl + "\" height=\"30\" width=\"25\">" + "Level:" + card.level + ", Number:" + card.getCurrentGross() + "<br/>";
			}else if (o instanceof Epic){
				Epic card = (Epic) o;
					epic++;
				epicCurNum += card.getCurrentGross();
				epicCurGold += card.getCurrentGold();
				epicCurExp += card.getCurrentExp();
				epicRemainNum += card.getRemainGross();
				epicRemainGold += card.getRemainGold();
				epicRemainExp += card.getRemainExp();
				epicCardsInfo += "<img src=\"https://statsroyale.com/images/cards/full/" +card.imgUrl + "\" height=\"30\" width=\"25\">" + "Level:" + card.level + ", Number:" + card.getCurrentGross() + "<br/>";
			}else if (o instanceof Legendary){
				Legendary card = (Legendary) o;
					legendary++;
				legCurNum += card.getCurrentGross();
				legCurGold += card.getCurrentGold();
				legCurExp += card.getCurrentExp();
				legRemainNum += card.getRemainGross();
				legRemainGold += card.getRemainGold();
				legRemainExp += card.getRemainExp();
				legCardsInfo += "<img src=\"https://statsroyale.com/images/cards/full/" +card.imgUrl + "\" height=\"30\" width=\"25\">" + "Level:" + card.level + ", Number:" + card.getCurrentGross() + "<br/>";
			}
		}
		int totalNum = commonCurNum + rareCurNum + epicCurNum + legCurNum;
		int totalGold = commonCurGold + rareCurGold + epicCurGold + legCurGold;
		int totalExp = commonCurExp + rareCurExp + epicCurExp + legCurExp;
		int totalRemainNum = commonRemainNum + rareRemainNum + epicRemainNum + legRemainNum;
		int totalRemainGold = commonRemainGold + rareRemainGold + epicRemainGold + epicRemainGold;
		int totalRemainExp = commonRemainExp + rareRemainExp + epicRemainExp + legRemainExp;
		StringBuilder s = new StringBuilder();
		s.append("common:" + common + "<br/>");
		s.append(comCardsInfo);
		s.append("common number:" + commonCurNum + "<br/>");
		s.append("common extra number:" + commonRemainNum + "<br/>");
		s.append("common extra gold:" + commonRemainGold + "<br/>");
		s.append("rare:" + rare + "<br/>");
		s.append(rareCardsinfo);
		s.append("rare number:" + rareCurNum + "<br/>");
		s.append("rare extra number:" + rareRemainNum + "<br/>");
		s.append("rare extra gold:" + rareRemainGold + "<br/>");
		s.append("epic:" + epic + "<br/>");
		s.append(epicCardsInfo);
		s.append("epic number:" + epicCurNum + "<br/>");
		s.append("epic extra number:" + epicRemainNum + "<br/>");
		s.append("epic extra gold:" + epicRemainGold + "<br/>");
		s.append("legendary:" + legendary + "<br/>");
		s.append(legCardsInfo);
		s.append("legendary number:" + legCurNum + "<br/>");
		s.append("legendary extra number:" + legRemainNum + "<br/>");
		s.append("legendary extra gold:" + legRemainGold + "<br/>");
		s.append("total card number:" + totalNum + "<br/>");
		s.append("total extra card number:" + totalRemainNum + "<br/>");
		s.append("total extra gold:" + totalRemainGold + "<br/>");
		s.append("total extra Exp:" + totalRemainExp + "<br/>");
	    return s.toString();
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(DemoApplication.class, args);
	}
}
