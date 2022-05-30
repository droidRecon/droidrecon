from SubDomain import *
from PortTCP import *
from urllib.parse import urlsplit
import threading

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

def bannerEnum(port):
    return "Port-Banner"

def main(userInput):
    ###print(extractFe(userInput))
    proto,domainName = extractFe(userInput)

    op = SubEnum(proto,domainName)

    output["domain"]=domainName
    subDomainsList=[]

    #for i in op:
    #    print(i[0])


    for host in op:

        subInnerDomain={}
        subInnerDomain["domain"]=host[0]
        #port scanning
        portsFull=[]


        #Perform port scanning and return portNo,Service and Banner....
        ports=PortEnum(host[0])

        #print(ports)

        for port in ports:
            portDetials={}
            service = serviceEnum(port)
            banner = bannerEnum(port)
            portDetials["service"]=service
            portDetials["banner"]=banner
            portDetials["portNo"]=port

            portsFull.append(portDetials)



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