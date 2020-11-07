import os
from datetime import datetime
from urllib.request import Request, urlopen

# http://haapi-env.eba-y7jh4qpd.ap-northeast-1.elasticbeanstalk.com/api/healthcheck
SITE = os.environ['site']  # URL of the site to check, stored in the site environment variable
# "0"
EXPECTED = os.environ['expected']  # String expected to be on the page, stored in the expected environment variable

def validate(res):
    '''
    Return False to trigger the canary

    Currently this simply checks whether the EXPECTED string is present.
    However, you could modify this to perform any number of arbitrary
    checks on the contents of SITE.
    '''
    return EXPECTED in res


def lambda_handler(event, context):
    print('Checking {} at {}...'.format(SITE, event['time']))
    try:
        req = Request(SITE, headers={'User-Agent': 'AWS Lambda'})
        response = str(urlopen(req).read())
        print(response)
        if not validate(response):
            raise Exception('Validation failed')
    except:
        print('Check failed!')
        raise
    else:
        print('Check passed!')
        return event['time']
    finally:
        print('Check complete at {}'.format(str(datetime.now())))
