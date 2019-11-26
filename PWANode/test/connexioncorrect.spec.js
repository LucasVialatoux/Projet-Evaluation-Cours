const { Builder, By, Key, until } = require('selenium-webdriver')
const assert = require('assert')
const URL_SERVEUR = "http://192.168.74.217/"

describe('Connexion correct', function() {
  this.timeout(30000)
  let driver
  let vars
  beforeEach(async function() {
    driver = await new Builder().forBrowser('firefox').build()
    vars = {}
  })
  it('Connexion correct', async function() {
    await driver.get(URL_SERVEUR)
    await driver.findElement(By.id("connect_button_code")).click()
    await driver.findElement(By.id("inputID")).click()
    await driver.findElement(By.id("inputID")).sendKeys("a@a.a")
    await driver.findElement(By.id("inputMdp")).click()
    await driver.findElement(By.id("inputMdp")).sendKeys("a")
    await driver.findElement(By.css(".btn-primary")).click()
    await driver.close()
  })
})
