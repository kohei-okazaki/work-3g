package jp.co.ha.common.system.impl;

import jp.co.ha.common.system.PasswordEncoder;

public class Sha256PasswordEncoder implements PasswordEncoder {

	@Override
	public void echo() {
		System.out.println(this.getClass().getSimpleName() + ">>>");
	}

}
