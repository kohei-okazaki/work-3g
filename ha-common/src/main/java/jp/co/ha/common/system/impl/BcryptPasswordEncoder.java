package jp.co.ha.common.system.impl;

import jp.co.ha.common.system.PasswordEncoder;

public class BcryptPasswordEncoder implements PasswordEncoder {

	@Override
	public void echo() {
		System.out.println(this.getClass().getSimpleName() + ">>>");
	}

}
