// Generated by Selenium IDE
const { Builder, By, Key, until } = require('selenium-webdriver')
const assert = require('assert')

describe('Déconnexion', function() {
  this.timeout(30000)
  let driver
  let vars
  beforeEach(async function() {
    driver = await new Builder().forBrowser('firefox').build()
    vars = {}
  })
  it('Déconnexion', async function() {
    await driver.get("http://192.168.74.217/")
    await driver.findElement(By.id("connect_button_code")).click()
    await driver.findElement(By.id("inputID")).click()
    await driver.findElement(By.id("inputID")).sendKeys("a@a.a")
    await driver.findElement(By.id("inputMdp")).click()
    await driver.findElement(By.id("inputMdp")).sendKeys("a")
    await driver.findElement(By.css(".btn-primary")).click()
    await driver.findElement(By.id("signout_btn")).click()
    await driver.findElement(By.id("connect_button_code")).click()
    await driver.close()
  })
})
