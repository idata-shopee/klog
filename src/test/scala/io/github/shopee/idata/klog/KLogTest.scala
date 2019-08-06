package io.github.free.lock.klog

class KLogTest extends org.scalatest.FunSuite {
  test("base") {
    KLog.info("test", "1234")
  }
}
