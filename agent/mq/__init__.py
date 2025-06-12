from urllib.parse import quote_plus

def get_amqp_url(host, port, user, password, virtual_host):
    return f'amqp://{user}:{quote_plus(password)}@{host}:{port}/{quote_plus(virtual_host)}'