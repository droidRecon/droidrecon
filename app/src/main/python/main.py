from SubDomain import *
from PortTCP import *
from urllib.parse import urlsplit
import threading
import whois
from Wappalyzer import Wappalyzer, WebPage
from DirectoryList import *

output = {}


def extractFe(userInput):
    #extracting the domain name without protocols...
    base_url = "{0.netloc}".format(urlsplit(userInput))
    if base_url=="":
        base_url=userInput

    if "http://" in userInput:
        return "http",base_url
    elif "https://" in userInput:
        return "https",base_url
    else:
        return "https",base_url


def SubEnum(proto,domainName):
    sub = SubDomain(proto,domainName)

    sub.thread_list=[]
    for t in range(100):
        thread=threading.Thread(target=sub.bruteScan)
        sub.thread_list.append(thread)
    for thread in sub.thread_list:
        thread.start()
    for thread in sub.thread_list:
        thread.join(1.0)
    # #print(sub.discovered_subdomains)
    # #print(len(sub.discovered_subdomains))
    # #
    # #print("x.CRT.sh Subdomain")
    sub.crtScan()

    # print(sub.discovered_subdomains)
    # print(len(sub.discovered_subdomains))
    sub.thread_list2=[]
    for t in range(100):
        thread=threading.Thread(target=sub.method_status)
        sub.thread_list2.append(thread)
    for thread in sub.thread_list2:
        thread.start()
    for thread in sub.thread_list2:
        thread.join(1.0)
    # #subdomain output with status code and allowed methods...
    # #200 responses....
    # #print(sub.output_subdomains200)
    # #not 200 responses....
    # print("x.Sub Domain enumeration completed....")
    # print(sub.output_subdomains)
    # #perform the port scanning (on the discovered subdomains)....


    return sub.output_subdomains

def PortEnum(host):
    ObjPortScn = PortTCP(host)


    ObjPortScn.thread_list=[]
    for t in range(100):
        thread=threading.Thread(target=ObjPortScn.scanport)
        ObjPortScn.thread_list.append(thread)
    for thread in ObjPortScn.thread_list:
        thread.start()
    for thread in ObjPortScn.thread_list:
        thread.join(1.0)

    return ObjPortScn.discovered_ports

def serviceEnum(port):
    #return "Port-Service"
    try:
        return socket.getservbyport(int(port),'tcp')
    except OSError:
        return ""

# def bannerEnum(port):
#     return "Port-Banner"

def bannerEnum(domain,port):
    if port in [53,80,6980,443]:
        return ""
    try:
        s=socket.socket()
        s.settimeout(2)
        s.connect((domain,int(port)))
        banner=s.recv(1024)
        s.shutdown(1) # By convention, but not actually necessary
        s.close()
        #print(banner.decode("utf-8"))
        return banner.decode("utf-8")
    except Exception as e:
        return ""

#directory listing...
def directoryEnum(protocol,host):
    objDir = DirectoryList(protocol,host)
    objDir.thread_list=[]
    for t in range(100):
        thread=threading.Thread(target=objDir.scan)
        objDir.thread_list.append(thread)
    for thread in objDir.thread_list:
        thread.start()
    for thread in objDir.thread_list:
        thread.join(1.0)
    return objDir.output_directory


#whois lookup....
def whoisEnum(domain):
    try:
        w = whois.whois(domain)
        return str(w)
    except Exception as e:
        return "Exception on whois"

#technology enumeration...
def techEnum(protocol,domain):
    try:
        url = f"{protocol}://{domain}"
        webpage = WebPage.new_from_url(url)
        wappalyzer = Wappalyzer.latest()
        #wappalyzer.analyze(webpage)
        return str(wappalyzer.analyze_with_versions_and_categories(webpage))
    except Exception as e:
        return "Exception on Tech"

def main(userInput):
    ###print(extractFe(userInput))
    proto,domainName = extractFe(userInput)

    op = SubEnum(proto,domainName)

    output["domain"]=domainName
    subDomainsList=[]


    # if len(op)==0:
    #     print("No subdomains Found!.....")
    #     #
    #     response = requests.get(f"{proto}://{domainName}")
    #     responseOpt = requests.options(f"{proto}://{domainName}")
    #     op[0]=[domainName,str(response.status_code),responseOpt.raw.getheader('allow')]



    for host in op:

        subInnerDomain={}
        subInnerDomain["domain"]=host[0]

        #storing the whois lookup...
        subInnerDomain["whois"]=whoisEnum(host[0])
        #techology identifications...
        subInnerDomain["tech"]=techEnum(proto,host[0])
        #directory enumeration...
        subInnerDomain["directory"]=directoryEnum(proto,host[0])

        #port scanning
        portsFull=[]


        #Perform port scanning and return portNo,Service and Banner....
        ports=PortEnum(host[0])

        #print(ports)

        for port in ports:
            portDetials={}
            service = serviceEnum(port)
            banner = bannerEnum(host[0],port)
            portDetials["service"]=service
            portDetials["banner"]=banner
            portDetials["portNo"]=port

            portsFull.append(portDetials)


        #directory listing...

        subInnerDomain["status"]=host[1]
        subInnerDomain["methods"]=host[2]
        subInnerDomain["ports"]=portsFull


        #adding to subdomain list...
        subDomainsList.append(subInnerDomain)


    output["subdomains"]=subDomainsList


    #print(op)
    #print("Dict - format")
    #print(output)
    return output