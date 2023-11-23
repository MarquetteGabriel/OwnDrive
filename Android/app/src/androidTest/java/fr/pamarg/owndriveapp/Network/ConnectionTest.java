package fr.pamarg.owndriveapp.Network;

import static org.junit.Assert.assertTrue;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.pamarg.owndriveapp.viewmodel.MainActivityViewModel;

public class ConnectionTest
{
    @Test
    public void getIpAddressTest()
    {
        MainActivityViewModel mainActivityViewModel = new MainActivityViewModel();
        Connection connection = new Connection(InstrumentationRegistry.getInstrumentation().getContext(), mainActivityViewModel);
        connection.getIpAddress();
        String regexPattern = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(String.valueOf(mainActivityViewModel.getIpAddress()));

        assertTrue(matcher.matches());
    }
}