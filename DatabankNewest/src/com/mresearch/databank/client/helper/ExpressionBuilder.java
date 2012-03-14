package com.mresearch.databank.client.helper;

import java.util.HashMap;
import java.util.Map;

/** Компилятор выражений */
public class ExpressionBuilder {
  
  private String expression; // Строка с исходным выражением
  private int p = 0; // текущая позиция
  
  public static Expression build(String expression) {
	ExpressionBuilder builder = new ExpressionBuilder(expression);
    builder.skip(" ");
    Expression expr = builder.build(0);
    return expr;
  }
  
  private ExpressionBuilder(String expression) {
    this.expression = expression;
  }
  
  
  /** Построить узел выражения */
  Expression build(int state) {
    if(lastState(state)) {
      Expression ex = null;
      boolean isMinus = startWith("-");
      if(isMinus)
        skip("-");

      if(startWith("(")) {
        skip("(");
        ex = build(0);
        skip(")");
      }
      else 
        ex = readSingle();
      if(isMinus)
        ex = new Expression.Unary(ex, "-");
      return ex;
    }
    
    boolean unarNot = state == 2 && startWith("!");
    if(unarNot)
      skip("!");
    
    /* Строим первый операнд */
    Expression a1 = build(state+1);
    if(unarNot)
      a1 = new Expression.Unary(a1, "!");
    
    // строим последущие операнды
    String op = null; 
    while((op = readStateOperator(state)) != null) {
      Expression a2 = build(state + 1);
      a1 = new Expression.Binary(a1, a2, op);
      
    }
    return a1;
  }
  
  private static String [][] states = new String[][] {
    {"||"},
    {"&&"},
    {"!"},
    { "<=", ">=", "==", "!=", "<", ">"},
    {"+", "-"},
    {"*", "/"},
    null
  };
  
  private boolean lastState(int s) {
    return s+1 >= states.length;
  }
  
  private boolean startWith(String s) {
    return expression.startsWith(s, p);
  }
  
  private void skip(String s) {
    if(startWith(s))
      p+= s.length();
    while(p < expression.length() && expression.charAt(p) == ' ')
      p++;
  }
  
  
  private String readStateOperator(int state) {
    String[] ops = states[state];
    for(String s : ops) {
      if(startWith(s)) {
        skip(s);
        return s;
      }
    }
    return null;
  }
  
  /**
   * считываем из потока "простое" значение (имя переменной, число или строку)
   * @return
   */
  private Expression readSingle() {
    int p0 = p;
    // чиатем из потока строку
    if(startWith("'") || startWith("\"")) {
      boolean q2 = startWith("\"");
      p = expression.indexOf(q2 ? '"' : '\'', p+1);
      Expression ex = new Expression.Str(expression.substring(p0+1, p));
      skip(q2 ? "\"" : "'");
      return ex;
    }
    
    // в потоке не строка => число или переменная
    while(p < expression.length()) {
      if(!(Character.isLetterOrDigit(expression.charAt(p))))
        break;
      p++;
    }
    
    Expression ex = null;
    if(p > p0) {
      String s = expression.substring(p0, p);
      skip(" ");
      try{
        // из потока прочитали число
        long x = Long.parseLong(s);
        return new Expression.Num(x);
      }
      catch(Exception e){}
      
      if("null".equals(s))
        return new Expression.Str(null);
      
      // не строка, не число и не null — значит переменная
      return new Expression.Var(s);
      
    }
    return null;
  }
  
  
  
  
  
  // для юнит-тестов
  public ExpressionBuilder(){}
  
 // @Test
  public void testBuilder() throws Exception {
    String str = "qwerty";
    double n1 = 10;
    double n2 = 5;
    
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("str", "str");
    map.put("n1", n1);
    map.put("n2", n2);
    
    Expression e = ExpressionBuilder.build("str != 'qwerty' && n1 / n2 >= 3 * (n2 + 10 / n1 * (2+3))");
    Boolean a = (Boolean) e.execute(map);
    Boolean b = !"qwerty".equals(str) && n1 / n2 >= 3 * (n2 + 10 / n1 * (2+3));
  //  assertTrue(a == b);
  }
}

