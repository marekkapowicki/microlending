package com.marekk.microlending.domain.application.validation

import com.marekk.microlending.BaseITSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = ApplicationValidationRulesConfiguration)
class IpValidationServiceITSpec extends BaseITSpec {

    @Autowired
    private IpValidationService service

    def "should retrieve 0 hits"() {
        given:
            InetAddress host = InetAddress.getByName("132.0.0.1")
        when:
            int counter = service.getHitCount(host)
        then:
            counter == 0
    }

    def 'should retrieve 0 hits after cache evict'() {
        given:
            InetAddress someHost = InetAddress.localHost
            service.rememberIpHit(someHost)
            service.flushIpsDaily()
        when:
            int counter = service.getHitCount(someHost)
        then:
            counter == 0

    }

    def 'should retrieve 2 ip hits from same host'() {
        given:
            InetAddress someHost = InetAddress.localHost
            InetAddress otherHost = InetAddress.getByName("132.0.0.1")
        and: 'hits from localhost are saved twice'
            service.rememberIpHit(someHost)
            service.rememberIpHit(someHost)
        and: 'hit from other host is saved'
            service.rememberIpHit(otherHost)
        when:
            int counter = service.getHitCount(someHost)
        then:
            counter == 2

    }
}
