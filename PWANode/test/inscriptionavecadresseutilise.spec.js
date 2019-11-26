// Generated by Selenium IDE
const { Builder, By, Key, until } = require('selenium-webdriver')
const assert = require('assert')

describe('Inscription avec adresse utilisée', function() {
  this.timeout(30000)
  let driver
  let vars
  beforeEach(async function() {
    driver = await new Builder().forBrowser('firefox').build()
    vars = {}
  })
  it('Inscription avec adresse utilisée', async function() {
    await driver.get("http://192.168.74.217/")
    await driver.findElement(By.id("connect_button_code")).click()
    await driver.findElement(By.id("inscription_button_code")).click()
    await driver.findElement(By.id("inputID")).click()
    await driver.findElement(By.id("inputID")).sendKeys("a@a.a")
    await driver.findElement(By.id("inputMdp1")).click()
    await driver.findElement(By.id("inputMdp1")).sendKeys("test")
    await driver.findElement(By.id("inputMdp2")).click()
    await driver.findElement(By.id("inputMdp2")).sendKeys("test")
    await driver.findElement(By.css(".btn-primary")).click()
    assert(await driver.findElement(By.id("retourID")).getText() == "Cette adresse email existe déjà.")
    await driver.close()
  })
})
