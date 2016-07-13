package com.lorin.drools;

//import org.kie.api.io.ResourceType;
import com.lorin.drools.fact.BigDecimalFact;
import com.lorin.drools.fact.OriginalMapFact;
import com.lorin.drools.fact.StringFact;
import org.drools.core.base.RuleNameEndsWithAgendaFilter;
import org.drools.core.base.RuleNameStartsWithAgendaFilter;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.io.impl.ReaderResource;
import org.kie.api.io.ResourceType;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;
import org.kie.internal.runtime.StatelessKnowledgeSession;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by baidu on 16/7/13.
 */
public class RuleTest {

    public static final void main(String[] args) throws Exception {
        RuleTest launcher = new RuleTest();
        launcher.executeExample();
        launcher.fireAllRules();
        launcher.fireActualRules();
    }

    public int executeExample() throws Exception {
        KnowledgeBuilder kbuilder1 = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder1.add( ResourceFactory.newClassPathResource("discountrule.drl", getClass()), ResourceType.DRL);

        if ( kbuilder1.hasErrors() ) {
            System.err.println("hava a error");
            System.err.print( kbuilder1.getErrors() );
            return -1;
        }
        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages( kbuilder1.getKnowledgePackages());
        //stateless session
        StatelessKnowledgeSession klsession = kbase.newStatelessKnowledgeSession();
        Order order1 = new Order();
        order1.setSumprice(159);

        klsession.execute(Arrays.asList(new Object[]{order1}));

        System.out.println("kbuilder DISCOUNT IS: " + order1.getDiscountPercent());

        return order1.getDiscountPercent();
    }


    public int fireAllRules() throws Exception {

        KnowledgeBuilder kbuilder2 = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder2.add(new ReaderResource(new StringReader(ruleStr)),ResourceType.DRL);


        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages( kbuilder2.getKnowledgePackages());

        //StatefulKnowledgeSession session
        StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
        Order order = new Order();
        order.setSumprice(159);
        ksession.insert(order);
        ksession.fireAllRules();
        System.out.println("kbuilder2 DISCOUNT IS: " + order.getDiscountPercent());
        return order.getDiscountPercent();
    }

    public int fireActualRules() throws Exception {
        //规则
        KnowledgeBuilder kbuilder3 = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder3.add( ResourceFactory.newClassPathResource("actualRule.drl", getClass()), ResourceType.DRL);

        if ( kbuilder3.hasErrors() ) {
            System.err.println("kbuilder3 hava a error");
            System.err.print( kbuilder3.getErrors() );
            return -1;
        }

        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages( kbuilder3.getKnowledgePackages());

        //StatefulKnowledgeSession session
        StatefulKnowledgeSession statefulSession = kbase.newStatefulKnowledgeSession();


        //数据收集Fact
        List<Object> list = new ArrayList<>();
        //StringFact
        StringFact merchantFact = new StringFact();
        merchantFact.setName("IPProvinceName_Fact");
        merchantFact.setValue(new String("河南"));
        list.add(merchantFact);

        //BigDecimalFact
        BigDecimalFact bigDecimalFact = new BigDecimalFact();
        bigDecimalFact.setName("BA00A100A1_BlankList_NewMemberId_Fact");
        bigDecimalFact.setValue(BigDecimal.valueOf(1));
        list.add(bigDecimalFact);

        //OriginalMapFact
        OriginalMapFact originalMapFact = new OriginalMapFact();
        originalMapFact.setName("originalMap");
        Map<String,String> originalMap = new HashMap<String,String>();
        originalMap.put("tradeAmount", "300");
        originalMap.put("orderTypeId", "23");
        originalMapFact.setValue(originalMap);
        list.add(originalMapFact);

        //全局map获得返回值
        Map<String, String> resultMap = new HashMap<String, String>();
        statefulSession.setGlobal("map", resultMap);

        for (int i = 0; i < list.size(); i++) {
            statefulSession.insert(list.get(i));
        }

        statefulSession.fireAllRules();

        System.out.println("result: " + resultMap);
        return 0;
    }


    private static String ruleStr = "package com.baidu.test //created on: 2009-11-11\n" +
            "\n" +
            "\n" +
            "import com.baidu.test.Order; //list any import classes here.\n" +
            "\n" +
            "\n" +
            "//declare any global variables here\n" +
            "\n" +
            "\n" +
            "\n" +
            "rule \"First Rule\"\n" +
            "\t\n" +
            "\twhen\n" +
            "\t\t//conditions\n" +
            "\t\torder:Order(sumprice>30,sumprice<=50);\n" +
            "\tthen \n" +
            "\t\t//actions\n" +
            "\t\torder.setDiscountPercent(98);\n" +
            "\t\t\n" +
            "end\n" +
            "\n" +
            "rule \"Second Rule\"\n" +
            "\t//include attributes such as \"salience\" here...\n" +
            "\twhen\n" +
            "\t\t//conditions\n" +
            "\t\torder:Order(sumprice>50,sumprice<=100);\n" +
            "\tthen \n" +
            "\t\t//actions\n" +
            "\t\torder.setDiscountPercent(95);\n" +
            "\t\t\n" +
            "end\n" +
            "\n" +
            "rule \"third Rule\"\n" +
            "\t//include attributes such as \"salience\" here...\n" +
            "\twhen\n" +
            "\t\t//conditions\n" +
            "\t\torder:Order(sumprice>100);\t\t\n" +
            "\tthen \n" +
            "\t\t//actions\t\t\n" +
            "\t\torder.setDiscountPercent(90);\n" +
            "end";
}
