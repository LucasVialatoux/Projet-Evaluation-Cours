// Generated by Selenium IDE
const { Builder, By, Key, until } = require('selenium-webdriver')
const assert = require('assert')

describe('Saisie code invalide', function() {
  this.timeout(30000)
  let driver
  let vars
  beforeEach(async function() {
    driver = await new Builder().forBrowser('firefox').build()
    vars = {}
  })
  it('Saisie code invalide', async function() {
    await driver.get("http://192.168.74.217/")
    await driver.findElement(By.css("div:nth-child(4) > .btn-group-default > .btn:nth-child(1)")).click()
    await driver.findElement(By.css("div:nth-child(4) .btn:nth-child(3)")).click()
    await driver.findElement(By.css(".btn-group-default:nth-child(2) > .btn:nth-child(2)")).click()
    await driver.findElement(By.css(".btn-group-default:nth-child(2) > .btn:nth-child(2)")).click()
    {
      const element = await driver.findElement(By.css(".btn-group-default:nth-child(2) > .btn:nth-child(2)"))
      await driver.actions({ bridge: true}).doubleClick(element).perform()
    }
    await driver.findElement(By.css(".btn-group-default:nth-child(2) > .btn:nth-child(2)")).click()
    await driver.findElement(By.css(".btn-success")).click()
    assert(await driver.findElement(By.id("retour")).getText() == "Code incorrect, veuillez le vérifier.")
    await driver.close()
  })
})