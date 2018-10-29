package io.github.shopee.idata.klog

class KLogTest extends org.scalatest.FunSuite {
  test("base") {
    KLog.info("test", "1234")
  }
}
