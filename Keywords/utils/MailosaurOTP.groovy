package utils
import com.mailosaur.MailosaurClient
import com.mailosaur.models.SearchCriteria
import com.mailosaur.models.Message

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

public class MailosaurOTP {
	

    @Keyword
    def getOtpWithWait(int timeoutSeconds = 60) {

        MailosaurClient client =
                new MailosaurClient(GlobalVariable.MAILOSAUR_API_KEY)

        SearchCriteria criteria = new SearchCriteria()
        criteria.subjectContains = "OTP"

        Message message = client.messages.get(
                GlobalVariable.MAILOSAUR_SERVER_ID,
                criteria,
                timeoutSeconds * 1000
        )

        if (message == null) {
            return null
        }

        // Ambil HTML body
        String html = message.html?.body
        if (html == null) {
            return null
        }

        // Strip HTML tag
        String text = html
                .replaceAll("<[^>]*>", " ")
                .replaceAll("\\s+", " ")

        println "MAILOSAUR PARSED TEXT:"
        println text

        // Ambil OTP 6 digit
        def matcher = text =~ /(\\d{6})/
        return matcher ? matcher[0][0] : null
    }
}
