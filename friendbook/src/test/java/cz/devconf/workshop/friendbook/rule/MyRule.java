package cz.devconf.workshop.friendbook.rule;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class MyRule implements TestRule {

	public Statement apply(final Statement base, Description description) {
		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				try {
					base.evaluate();
					System.out.println("Place a breakpoint here");
				} catch (Throwable e) {
					throw e;
				}
			}
		};
	}

}