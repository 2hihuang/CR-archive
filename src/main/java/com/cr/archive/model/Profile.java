package com.cr.archive.model;

import com.cr.archive.cardtype.Common;
import com.cr.archive.cardtype.Epic;
import com.cr.archive.cardtype.Legendary;
import com.cr.archive.cardtype.Rare;
import com.cr.archive.util.HttpUtil;
import sun.security.util.Length;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Profile {

    private String tag;
    private Map<String, String> chestsQueue = new LinkedHashMap<>();
    private List<Object> cardSet = new ArrayList<>();
    private static Map<String, String> cardsRarity = new HashMap<String, String>(){{
        put("Knight", "common");
        put("Archers", "common");
        put("Goblins", "common");
        put("Giant", "rare");
        put("P.E.K.K.A", "epic");
        put("Minions", "common");
        put("Balloon", "epic");
        put("Witch", "epic");
        put("Barbarians", "common");
        put("Golem", "epic");
        put("Skeletons", "common");
        put("Valkyrie", "rare");
        put("Skeleton+Army", "epic");
        put("Bomber", "common");
        put("Musketeer", "rare");
        put("Baby+Dragon", "epic");
        put("Prince", "epic");
        put("Wizard", "rare");
        put("Mini+P.E.K.K.A", "rare");
        put("Spear+Goblins", "common");
        put("Giant+Skeleton", "epic");
        put("Hog+Rider", "rare");
        put("Minion+Horde", "common");
        put("Ice+Wizard", "legendary");
        put("Royal+Giant", "common");
        put("Guards", "epic");
        put("Princess", "legendary");
        put("Dark+Prince", "epic");
        put("Three+Musketeers", "rare");
        put("Lava+Hound", "legendary");
        put("Ice+Spirit", "common");
        put("Fire+Spirits", "common");
        put("Miner", "legendary");
        put("Sparky", "legendary");
        put("Bowler", "epic");
        put("Lumberjack", "legendary");
        put("Battle+Ram", "rare");
        put("Ice+Golem", "rare");
        put("Mega+Minion", "rare");
        put("Dart+Goblin", "rare");
        put("Goblin+Gang", "common");
        put("Electro+Wizard", "legendary");
        put("Elite+Barbarians", "common");
        put("Hunter", "epic");
        put("Executioner", "epic");
        put("Bandit", "legendary");
        put("Bats", "common");
        put("Royal+Ghost", "legendary");
        put("Zappies", "rare");
        put("Cannon+Cart", "epic");
        put("Skeleton+Barrel", "common");
        put("Flying+Machine", "rare");
        put("Cannon", "common");
        put("Goblin+Hut", "rare");
        put("Mortar", "common");
        put("Inferno+Tower", "rare");
        put("Bomb+Tower", "rare");
        put("Barbarian+Hut", "rare");
        put("Tesla", "common");
        put("Elixir+Collector", "rare");
        put("X-Bow", "epic");
        put("Tombstone", "rare");
        put("Furnace", "rare");
        put("Fireball", "rare");
        put("Arrows", "common");
        put("Rage", "epic");
        put("Rocket", "rare");
        put("Goblin+Barrel", "epic");
        put("Freeze", "epic");
        put("Mirror", "epic");
        put("Lightning", "epic");
        put("Zap", "common");
        put("Poison", "epic");
        put("The+Log", "legendary");
        put("Tornado", "epic");
        put("Clone", "epic");
        put("Heal", "rare");
        put("Inferno+Dragon", "legendary");
        put("Night+Witch", "legendary");
        put("Graveyard", "legendary");
        put("Mega+Knight", "legendary");
        put("Magic+Archer", "legendary");
        put("Barbarian+Barrel", "epic");
    }};

    public Profile(String tag) {
        this.tag = tag;
        initProfile();
    }

    private void initProfile() {
        String profile = HttpUtil.get(this.tag);

        String pattern = "<span class=\"chests__counter\">[\\s\\S]*?</div>";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(profile);
        while (m.find()) {
            Pattern r1 = Pattern.compile("(?<=(counter\">))[\\s\\S]*?(?=</span>)");
            Pattern r2 = Pattern.compile("([a-zA-Z](\\s)*).*?(Chest)");
            String text = m.group();
            Matcher m1 = r1.matcher(text);
            Matcher m2 = r2.matcher(text);
            while (m1.find() && m2.find()) {
                chestsQueue.put(m1.group(), m2.group());
            }
        }
        initCards(this.tag);
    }

    private void initCards(String tag) {
        String cardStr = HttpUtil.getCards(this.tag);
        //System.out.println(cardStr);
        String pattern = "<div class=\"profileCards__card\\s[\\s\\S]*?</div>";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(cardStr);
        while (m.find()) {


            Pattern r1 = Pattern.compile("(?<=(\\/card\\/))[\\s\\S]*?(?=\")");
            Pattern r2 = Pattern.compile("(?<=(lvl\\.))[0-9]*(?=<)|(Max Lvl)");
            Pattern r3 = Pattern.compile("[0-9]*(?=\\/[0-9])");
            Pattern r4 = Pattern.compile("(?<=(\\/full\\/))[\\s\\S]*?(?=\")");
            String text = m.group();
            Matcher m1 = r1.matcher(text);
            Matcher m2 = r2.matcher(text);
            Matcher m3 = r3.matcher(text);
            Matcher m4 = r4.matcher(text);
            while (m1.find()) {
                String name = m1.group();
                String imgUrl = (m4.find() ? m4.group() : "");
                String level =  (m2.find() ? m2.group() : "0");
                String number = (m3.find() ? m3.group() : "0");
                System.out.println("name:" + name);
                System.out.println("level:" + level);
                System.out.println("number:" + number);
                System.out.println("imgUrl:" + imgUrl);
                String rarity = cardsRarity.get(name);
                if ("common".equals(rarity)){
                    if ("Max Lvl".equals(level)) level = "13";
                    cardSet.add(new Common(name, Integer.parseInt(level), Integer.parseInt(number), imgUrl));
                }else if ("rare".equals(rarity)){
                    if ("Max Lvl".equals(level)) level = "11";
                    cardSet.add(new Rare(name, Integer.parseInt(level), Integer.parseInt(number), imgUrl));
                }else if ("epic".equals(rarity)){
                    if ("Max Lvl".equals(level)) level = "8";
                    cardSet.add(new Epic(name, Integer.parseInt(level), Integer.parseInt(number), imgUrl));
                }else if ("legendary".equals(rarity)){
                    if ("Max Lvl".equals(level)) level = "5";
                    cardSet.add(new Legendary(name, Integer.parseInt(level), Integer.parseInt(number), imgUrl));
                }
            }
        }
        cardSet.sort(new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                String name1 = null;
                String name2 = null;
                try {
                    name1 = (String) o1.getClass().getDeclaredField("name").get(o1);
                    name2 = (String) o2.getClass().getDeclaredField("name").get(o2);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                return name1.compareTo(name2);
            }
        });
    }

    public Map<String, String> getChestsQueue() {
        return chestsQueue;
    }

    public List<Object> getCardSet() {
        return cardSet;
    }

    public static void main(String[] args) {
        //Profile p = new Profile("82UYLC9J");
        Profile p = new Profile("2vpjvclc");
        //System.out.println(p.cardsRarity.size());
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

        for (Object o : p.cardSet) {
            if (o instanceof Common){
                Common card = (Common) o;
                    common++;


                commonCurNum += card.getCurrentGross();
                commonCurGold += card.getCurrentGold();
                commonCurExp += card.getCurrentExp();
                commonRemainNum += card.getRemainGross();
                commonRemainGold += card.getRemainGold();
                commonRemainExp += card.getRemainExp();
            }else if (o instanceof Rare){
                Rare card = (Rare) o;
                    rare++;
                rareCurNum += card.getCurrentGross();
                rareCurGold += card.getCurrentGold();
                rareCurExp += card.getCurrentExp();
                rareRemainNum += card.getRemainGross();
                rareRemainGold += card.getRemainGold();
                rareRemainExp += card.getRemainExp();
            }else if (o instanceof Epic) {
                Epic card = (Epic) o;
                    epic++;
                epicCurNum += card.getCurrentGross();
                epicCurGold += card.getCurrentGold();
                epicCurExp += card.getCurrentExp();
                epicRemainNum += card.getRemainGross();
                epicRemainGold += card.getRemainGold();
                epicRemainExp += card.getRemainExp();
            }else if (o instanceof Legendary){
                Legendary card = (Legendary) o;
                    legendary++;
                legCurNum += card.getCurrentGross();
                legCurGold += card.getCurrentGold();
                legCurExp += card.getCurrentExp();
                legRemainNum += card.getRemainGross();
                legRemainGold += card.getRemainGold();
                legRemainExp += card.getRemainExp();
            }
        }
        int totalNum = commonCurNum + rareCurNum + epicCurNum + legCurNum;
        int totalGold = commonCurGold + rareCurGold + epicCurGold + legCurGold;
        int totalExp = commonCurExp + rareCurExp + epicCurExp + legCurExp;
        int totalRemainNum = commonRemainNum + rareRemainNum + epicRemainNum + legRemainNum;
        int totalRemainGold = commonRemainGold + rareRemainGold + epicRemainGold + epicRemainGold;
        int totalRemainExp = commonRemainExp + rareRemainExp + epicRemainExp + legRemainExp;
        System.out.println("common:" + common);
        System.out.println("common number:" + commonCurNum);
        System.out.println("common extra number:" + commonRemainNum);
        System.out.println("common extra gold:" + commonRemainGold);
        System.out.println("rare:" + rare);
        System.out.println("rare number:" + rareCurNum);
        System.out.println("rare extra number:" + rareRemainNum);
        System.out.println("rare extra gold:" + rareRemainGold);
        System.out.println("epic:" + epic);
        System.out.println("epic number:" + epicCurNum);
        System.out.println("epic extra number:" + epicRemainNum);
        System.out.println("epic extra gold:" + epicRemainGold);
        System.out.println("legendary:" + legendary);
        System.out.println("legendary number:" + legCurNum);
        System.out.println("legendary extra number:" + legRemainNum);
        System.out.println("legendary extra gold:" + legRemainGold);
        System.out.println("total card number:" + totalNum);
        System.out.println("total extra card number:" + totalRemainNum);
        System.out.println("total extra gold:" + totalRemainGold);
        System.out.println("total extra Exp:" + totalRemainExp);
    }
}
