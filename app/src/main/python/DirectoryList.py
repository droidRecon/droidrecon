import requests
from bs4 import BeautifulSoup

class DirectoryList:

    def __init__(self,protocol,host):
        self.directory=['admin', 'about', 'Contact', 'ContactUs', 'Download', 'Downloads', 'Image', 'Images', 'Log', 'Login', 'Logs', 'Media', 'Office', 'Pages', 'People', 'Press', 'Scripts', 'Search', 'Security', 'Services', 'SiteMap', 'SiteServer', 'Sites', 'Sources', 'about-us', 'aboutus', 'academic', 'academics', 'access', 'admin_login', 'administration', 'administrator', 'adminlogin', 'adminsql', 'album', 'albums', 'apache', 'api', 'auth', 'back-up', 'backend', 'background', 'backoffice', 'backup', 'backups', 'career', 'careers', 'cgi', 'cgi-bin', 'cgi-home', 'cgi-local', 'cgi-sys', 'cgibin', 'common', 'communications', 'community', 'data', 'database', 'databases', 'db', 'default', 'defaults', 'deployment', 'dir', 'directories', 'directory', 'dirphp', 'discovery', 'file', 'fileadmin', 'forget', 'forgot', 'general', 'guest', 'group', 'groups', 'hidden', 'hide', 'history', 'index', 'index1', 'index2', 'index3', 'lib', 'libraries', 'library', 'logout', 'mail', 'mailbox', 'mqseries', 'mrtg', 'ms', 'ms-sql', 'msadc', 'msn', 'msql', 'mssql', 'my', 'my-sql', 'myaccount', 'mysql', 'network', 'networking', 'new', 'news', 'offers', 'office', 'old', 'online', 'password', 'passwords', 'php', 'phpMyAdmin', 'phpmyadmin', 'phppgadmin', 'phpinfo', 'phps', 'pic', 'pics', 'pictures', 'request', 'research', 'resource', 'resources', 'save', 'src', 'script', 'scripts', 'search', 'secret', 'secrets', 'section', 'sections', 'secure', 'signin', 'signup', 'simple', 'single', 'site', 'site-map', 'site_map', 'sitemap', 'sites', 'smb', 'sql', 'sqladmin', 'sys', 'sysadmin', 'system', 'system_web', 'table', 'target', 'tmp', 'updates', 'upgrade', 'upload', 'uploader', 'uploads', 'url', 'webadmin', 'webapp', 'webboard', 'wp', 'wp-admin', 'wp-content', 'wp-includes', 'wp-login', 'wp-register', 'zip', 'zipfiles', 'zips', '~adm', '~admin', '~administrator', '~bin', '~ftp', '~guest', '~mail', '~operator', '~root', '~sys', '~sysadm', '~sysadmin', '~test', '~user', '~webmaster', '~www']
        self.protocol=protocol
        self.host=host
        self.output_directory=[]


    def scan(self):
        headers={"User-Agent": "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2049.0 Safari/537.36"}
        dir = self.directory
        while(len(dir)):
            i=dir.pop(0)
            url=f"{self.protocol}://{self.host}/{i}/"
            domain_p=url

            try:
                response = requests.get(url,timeout=5)
                output = {}
                #print(url+" --> "+str(response.status_code))
                if response.status_code!=404:
                    output["path"]=url
                    output["status"]=str(response.status_code)
                    finalurlOp=[]
                    if response.status_code==200:
                        #output["urls"]=fetch()
                        #fetching urls
                        outputurl=[]
                        #anchor tab scarpping...
                        soup = BeautifulSoup(response.text,'html.parser')
                        for a in soup.find_all('a', href=True):
                            urls_href = a['href']
                            if urls_href.startswith("/"):
                                #print(domain_p+urls_href)
                                outputurl.append(domain_p+urls_href)
                            elif urls_href.startswith("#"):
                                pass
                            else:
                                if domain_p in urls_href:
                                    #print(urls_href)
                                    outputurl.append(urls_href)
                        #scrpping img tags....
                        for b in soup.find_all('img', src=True):
                            urls_src = b['src']
                            if urls_src.startswith("/"):
                                #print(domain_p+urls_src)
                                outputurl.append(domain_p+urls_src)
                            elif urls_src.startswith("#"):
                                pass
                            else:
                                if domain_p in urls_src:
                                    #print(urls_src)
                                    outputurl.append(urls_src)
                        #scrapping script tags...
                        for c in soup.find_all('script', src=True):
                            script_src = c['src']
                            if script_src.startswith("/"):
                                #print(domain_p+script_src)
                                outputurl.append(domain_p+script_src)
                            elif script_src.startswith("#"):
                                pass
                            else:
                                if domain_p in script_src:
                                    #print(script_src)
                                    outputurl.append(script_src)

                        #removing duplicates
                        finalurlOp=set(outputurl)

                    #saving to dict...
                    output["urls"]=list(finalurlOp)
                    self.output_directory.append(output)
            except Exception as e:
                #print(e)
                pass
