import socket

class PortTCP:
    def __init__(self,host):
        self.target=host
        self.ports = list(range(1,65535))
        self.discovered_ports = []

    def scanport(self):
        while len(self.ports):
            port = self.ports.pop(0)
            try:
                #sock=socket.socket(socket.AF_INET,socket.SOCK_STREAM)
                #sock.connect((self.target,port))
                #sock.close()

                sock=socket.socket(socket.AF_INET,socket.SOCK_STREAM)
                sock.settimeout(1)
                sock.connect((self.target,port))
                sock.close()


                self.discovered_ports.append(port)
                #print(port)
                #print(url+" : "+str(response.status_code))
            except:
                # if the subdomain does not exist, just pass, print nothing
                pass
            else:
                #self.discovered_subdomains.append(fullname)
                pass
