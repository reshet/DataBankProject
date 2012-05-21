package com.mresearch.databank.client.helper;

import java.util.Map;

public abstract class Expression {
	  
	  /** ¬ычислить выражение дл€ даных значений переменных */
	  public abstract Object execute(Map<String, Object> values) throws Exception;
	  
	  
	  /** ”зел дерева Ч Ђ„ислої */
	  static class Num extends Expression {
	    private final double value;
	    
	    public Num(double x) {
	      value = x;
	    }
	    
	    @Override
	    public Object execute(Map<String, Object> values) {
	      return value;
	    }
	  }
	  
	  /** ”зел дерева Ч Ђ—трокаї */
	  static class Str extends Expression {
	    private final String value;
	    
	    public Str(String x) {
	      value = x;
	    }
	    
	    @Override
	    public Object execute(Map<String, Object> values) {
	      return value;
	    }
	  }  

	  /** ”зел дерева Ч Ђѕеременна€ї */
	  static class Var extends Expression {
	    private final String name;
	    
	    public Var(String name) {
	      this.name = name;
	    }
	    
	    @Override
	    public Object execute(Map<String, Object> values) {
	      return values.get(name);
	    }
	  }
	  
	  
	  /** ”зел дерева Ч Ђ”нарный операторї */
	  static class Unary extends Expression {
	    private final Expression expr;
	    private final boolean not;
	    
	    public Unary(Expression e, String oper) {
	      expr = e;
	      not = "!".equals(oper);
	    }
	    
	    @Override
	    public Object execute(Map<String, Object> values) throws Exception {
	      Object o = expr.execute(values);
	      if(not)
	        return !(Boolean)o;
	      else
	        return -(Double)o;
	    }
	  }  
	  
	  

	  /** ”зел дерева Ч ЂЅинарный операторї */
	  static class Binary extends Expression {
	    private final Expression x1;
	    private final Expression x2;
	    private final String op;
	    
	    public Binary(Expression x1, Expression x2, String op) {
	      this.x1 = x1;
	      this.x2 = x2;
	      this.op = op;
	    }

	    @Override
	    public Object execute(Map<String, Object> values) throws Exception {
	      Object o1 = x1.execute(values);
	      Object o2 = x2.execute(values);
	      
	      Class type = commonType(o1, o2);
	      
	      if(type == String.class)
	        return execStr(o1 != null? o1.toString() : null, o2 != null ? o2.toString() : null);
	      else if(type == Double.class)
	        return execNum((Double)o1, (Double)o2);
	      else
	        return execBool((Boolean)o1, (Boolean)o2);
	    }
	    
	    private Class commonType(Object o1, Object o2) {
	      if(o1 == null || o2 == null || o1 instanceof String || o2 instanceof String)
	        return String.class;
	      if(o1 instanceof Double && o2 instanceof Double)
	        return Double.class;
	      return Boolean.class;
	    }
	    
	    private Object execStr(String s1, String s2) throws Exception {
	      if("==".equals(op))
	        return (Boolean)(s1 == null ? s2 == null : s1.equals(s2));
	      if("!=".equals(op))
	        return (Boolean)(s1 == null ? s2 != null : !s1.equals(s2));
	      if("+".equals(op))
	        return (String)(s1 == null ? s2 : s1 + (s2 == null ? "" : s2));
	      throw new Exception("Illegal String operator: " + op);
	    }

	    private Object execBool(boolean q1, boolean q2) throws Exception {    
	      if("&&".equals(op))
	        return q1 && q2;
	      if("||".equals(op))
	        return q1 || q2;
	      if("==".equals(op))
	        return q1 == q2;
	      if("!=".equals(op))
	        return q1 != q2;
	      throw new Exception("Illegal Boolean operator: " + op);
	    }

	    private Object execNum(double n1, double n2) throws Exception {    
	      if("==".equals(op))
	        return (Boolean)(Math.abs(n1 - n2) < 0.001);
	      if("!=".equals(op))
	        return (Boolean)(Math.abs(n1 - n2) > 0.001);
	      if("<".equals(op))
	        return (Boolean)(n1 < n2);
	      if("<=".equals(op))
	        return (Boolean)(n1 <= n2);
	      if(">".equals(op))
	        return (Boolean)(n1 > n2);
	      if(">=".equals(op))
	        return (Boolean)(n1 >= n2);
	      if("+".equals(op))
	        return (Double)(n1 + n2);
	      if("-".equals(op))
	        return (Double)(n1 - n2);
	      if("*".equals(op))
	        return (Double)(n1 * n2);
	      if("/".equals(op))
	        return (Double)(n1 / n2);
	      
	      throw new Exception("Illegal Long operator: " + op);
	    }
	  }
	}
