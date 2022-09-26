package com.aludey.sbertest

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite)
@Suite.SuiteClasses([
        PositiveWeatherTests,
        NegativeAPIErrorsTests
])
class TestSuiteSpecification {
}
