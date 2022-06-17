import requests
import regex


class SubDomain:
    def __init__(self,protocol,domain):
        self.protocol=protocol
        self.domain=domain
        self.subdomains = ['login', 'auth', 'signup', 'signin', 'registeration', 'www', 'ftp', 'mobile', 'video', 'image', 'static', 'app', 'service', 'tools', 'email', 'local', 'data', 'file', 'live', 'www.api', 'api', 'news', 'games', 'courses', 'result', 'downloads', 'status', 'admin', 'search', 'research', 'list', 'lite', 'site', 'user', 'account', 'administrator', 'localhost', 'smtp', 'pop', 'cmd', 'shop', 'beta', 'sql', 'secure', 'demo', 'web', 'portal', 'cdn', 'dns', 'apps', 'info', 'office', 'exchange', 'test', 'docs', 'dhcp', 'new', 'accounts', 'alpha', 'alumini', 'backup', 'backups', 'git', 'community', 'client', 'database', 'developers', 'development', 'gallery', 'go', 'google', 'guest', 'help', 'home', 'host', 'id', 'info', 'payment', 'php', 'phpmyadmin', 'server', 'public', 'code', 'siteadmin', 'sitebuilder', 'log', 'logs', 'staff', 'student', 'tunnel', 'www-data', 'location', 'locations', 'lib', 'files', 'class', 'edu', 'access', 'book', 'online']
        self.discovered_subdomains = []
        self.output_subdomains = []



    def bruteScan(self):
        #scanning using list....
        while len(self.subdomains):
            subdomain = self.subdomains.pop(0)
            fullname = f"{subdomain}.{self.domain}"
            url = f"{self.protocol}://{fullname}"
            try:
                response = requests.get(url)
                self.discovered_subdomains.append(fullname)
            except requests.ConnectionError:
                # if the subdomain does not exist, just pass, print nothing
                pass
            else:
                pass

    def crtScan(self):
        #scanning using crt.sh.....
        response = requests.get("https://crt.sh/?q="+self.domain)
        mylist = regex.findall('(?:[a-z0–9](?:[a-z0–9-]{0,61}[a-z0–9])?\.)+[a-z0–9][a-z0–9-]{0,61}[a-z0–9]',str(response.text))
        #sort out the domains that contains/end with the user inputted domain name...
        res = [idx for idx in mylist if idx.lower().endswith(self.domain.lower())]
        #removing duplication...
        [self.discovered_subdomains.append(x) for x in res if x not in self.discovered_subdomains]




    def method_status(self):
        #fetching status code and methods....
        opdomain = self.discovered_subdomains
        while len(opdomain):
            urlname=opdomain.pop(0)
            try:
                response = requests.get(f"{self.protocol}://{urlname}")
                responseOpt = requests.options(f"{self.protocol}://{urlname}")
                self.output_subdomains.append([urlname,str(response.status_code),responseOpt.raw.getheader('allow')])
            except:
                pass

    