package edu.stanford.nlp.sempre;

import com.google.common.base.Function;
import fig.basic.LispTree;

/**
 * Usage:
 *   (mark |var| |body|).
 * For example:
 *   (mark x (and person (likes (var x))))
 * is the set of people that like themselves.
 * Like lambda abstraction, marking introduces a variable,
 * but has the same type as the |body|.
 *
 * Percy Liang
 */
public class MarkFormula extends Formula {
  public final String var;
  public final Formula body;

  public MarkFormula(String var, Formula body) {
    this.var = var;
    this.body = body;
  }

  public LispTree toLispTree() {
    LispTree tree = LispTree.proto.newList();
    tree.addChild("mark");
    tree.addChild(var);
    tree.addChild(body.toLispTree());
    return tree;
  }

  public Formula map(Function<Formula, Formula> func) {
    Formula result = func.apply(this);
    return result == null ? new MarkFormula(var, body.map(func)) : result;
  }

  @Override
  public boolean equals(Object thatObj) {
    if (!(thatObj instanceof MarkFormula)) return false;
    MarkFormula that = (MarkFormula) thatObj;
    return this.var.equals(that.var) && this.body.equals(that.body);
  }
  
  public int computeHashCode() {
    int hash = 0x7ed55d16;
    hash = hash * 0xd3a2646c + var.hashCode();
    hash = hash * 0xd3a2646c + body.hashCode();
    return hash;
  }
}
