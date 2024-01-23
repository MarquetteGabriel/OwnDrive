package fr.pamarg.owndriveapp.view;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LoginFragmentTest {

    @Test
    public void validIpTest()
    {
        LoginFragment loginFragment = new LoginFragment();
        assertTrue(loginFragment.validIp("1.1.1.1"));
        assertTrue(loginFragment.validIp("10.10.10.10"));
        assertTrue(loginFragment.validIp("255.255.255.255"));
        assertFalse(loginFragment.validIp("1.1.1"));
        assertFalse(loginFragment.validIp("azre"));
    }
}