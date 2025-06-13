import logging

all = ['LOG_FORMAT', 'LOG_LEVEL', 'logger']

# utils/__init__.py Configuration
LOG_FORMAT = '%(asctime)s - %(name)s - %(levelname)s - %(message)s'
LOG_LEVEL = logging.WARNING

logging.basicConfig(format=LOG_FORMAT, level=LOG_LEVEL)
logger = logging.getLogger('agent')


