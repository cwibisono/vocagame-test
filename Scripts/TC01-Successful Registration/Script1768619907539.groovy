import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import utils.RandomNameGenerator as RandomNameGenerator

def generator = new RandomNameGenerator()

// generate test data
GlobalVariable.FIRST_NAME = CustomKeywords.'utils.RandomNameGenerator.generateFirstName'()
GlobalVariable.LAST_NAME  = CustomKeywords.'utils.RandomNameGenerator.generateLastName'()

// ambil otp
GlobalVariable.EMAIL = CustomKeywords.'utils.RandomNameGenerator.generateSimpleEmail'(
	'user',
	GlobalVariable.MAILOSAUR_SERVER_ID + '.mailosaur.net'
)

WebUI.openBrowser('')
WebUI.navigateToUrl(GlobalVariable.BASE_URL)

WebUI.verifyElementPresent(findTestObject('Home Page/button_Masuk'), 0)
WebUI.click(findTestObject('Home Page/button_Masuk'))

WebUI.verifyElementPresent(findTestObject('Login Page/span_Daftar Sekarang'), 0)
WebUI.click(findTestObject('Login Page/span_Daftar Sekarang'))

WebUI.verifyTextPresent('Daftar Akun Vocagame', false)

WebUI.setText(findTestObject('Registration/Nama Depan'), GlobalVariable.FIRST_NAME)
WebUI.setText(findTestObject('Registration/Nama Belakang'), GlobalVariable.LAST_NAME)
WebUI.setText(findTestObject('Registration/input_email'), GlobalVariable.EMAIL)
WebUI.setText(findTestObject('Registration/input_password'), 'Password123!')
WebUI.setText(findTestObject('Registration/confirm password'), 'Password123!')

WebUI.check(findTestObject('Registration/TCcheckbox'))
WebUI.click(findTestObject('Registration/button_Buat Akun'))

// kirim otp
WebUI.click(findTestObject('OTP Page/li_Kirim OTP via Emailasumm.com'))
WebUI.click(findTestObject('OTP Page/button_Lanjutkan'))

// ambil otp
String otp = CustomKeywords.'utils.MailosaurOTP.getOtpWithWait'(60)
assert otp != null : "OTP tidak ditemukan di email Mailosaur"

WebUI.verifyNotEqual(otp, null)

// input otp
WebUI.setText(findTestObject('input_otp'), otp)

WebUI.acceptAlert()
WebUI.waitForElementNotPresent(findTestObject('Login Page/button_Masuk Akun'), 10)
