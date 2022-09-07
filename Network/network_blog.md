这个是从各种大佬的博客里面选取出的计算机网络知识。

有应用层：http协议，rpc协议，传输层：tcp协议，udp协议。

很多细节。

# 一、既然有HTTP协议，为什么还要有RPC？

[![img](http://wx.qlogo.cn/mmhead/Q3auHgzwzM48bREm0nO6wjhCe2nwp7NUf2azfKAxCbDz9ZYLcvcxng/0)**小白debug**.答应我，关注之后，好好学技术，别只是收藏我的表情包。。](https://mp.weixin.qq.com/s?__biz=MzU1Nzg4NjgyMw==&mid=2247502507&idx=2&sn=aeec7d90eb6128917ad377ba750ff547&key=2d14964c488b73390d69c34d52c0e18787a08192fd427aaae7ef2512238d2fcc139879a034c87f6b89b022aaa7138ff22bc0bea9448a201a191c086bdeb46322938a7d5f57cc2d132446002aed8aa8678bd446fffb8a98fb39c97a267c6f8dc90c51706be67a46600c29585fc4d129e077941ebc63fa78e34b2469f3cacf5633&ascene=0&uin=MTk0ODQwMDA3NA%3D%3D&devicetype=Windows+10+x64&version=6307062c&lang=zh_CN&exportkey=AYgADRuI3oWHt%2F2Ha8VX03A%3D&acctmode=0&pass_ticket=oYA5t7xdopz8Wq91sGyo%2FI7wP5QMYP7BU5J937NREsJ%2Bf%2Bp77Karq%2FC1qcxuCJSe&wx_header=0&fontgear=2#)

我想起了我刚工作的时候，第一次接触RPC协议，当时就很懵，**我HTTP协议用的好好的，为什么还要用RPC协议？**

于是就到网上去搜。

不少解释显得非常官方，我相信大家在各种平台上也都看到过，解释了又好像没解释，都在**用一个我们不认识的概念去解释另外一个我们不认识的概念**，懂的人不需要看，不懂的人看了还是不懂。

这种看了，又好像没看的感觉，云里雾里的很难受，**我懂**。

为了避免大家有强烈的**审丑疲劳**，今天我们来尝试重新换个方式讲一讲。

## 从TCP聊起

作为一个程序员，假设我们需要在A电脑的进程发一段数据到B电脑的进程，我们一般会在代码里使用socket进行编程。

这时候，我们可选项一般也就TCP和UDP二选一。TCP可靠，UDP不可靠。除非是马总这种神级程序员（早期QQ大量使用UDP），否则，只要稍微对可靠性有些要求，普通人一般无脑选TCP就对了。

类似下面这样。

```
fd = socket(AF_INET,SOCK_STREAM,0);
```

其中`SOCK_STREAM`，是指使用**字节流**传输数据，说白了就是**TCP协议**。

在定义了socket之后，我们就可以愉快的对这个socket进行操作，比如用`bind()`绑定IP端口，用`connect()`发起建连。

![图片](https://mmbiz.qpic.cn/mmbiz_gif/AnAgeMhDIian70VibLxaCGiamEibMtALF3ypNrua4jD5rsvxq3lhEHYhHqmzzAaTeQhXINaDrYD8XicpOn5MYtib6Tow/640?wx_fmt=gif&wxfrom=5&wx_lazy=1)握手建立连接流程

在连接建立之后，我们就可以使用`send()`发送数据，`recv()`接收数据。

光这样一个纯裸的TCP连接，就可以做到收发数据了，那是不是就够了？

不行，这么用会有问题。

## 使用纯裸TCP会有什么问题

八股文常背，TCP是有三个特点，**面向连接**、**可靠**、基于**字节流**。

![图片](https://mmbiz.qpic.cn/mmbiz_png/AnAgeMhDIian70VibLxaCGiamEibMtALF3ypz8YfzAz1qnUV4XqP5qsZYfYGqPic7KwmqJm6gzKCIWgHQX49aLyr7jQ/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)TCP是什么

这三个特点真的概括的**非常精辟**，这个八股文我们没白背。

每个特点展开都能聊一篇文章，而今天我们需要关注的是**基于字节流**这一点。

字节流可以理解为一个双向的通道里流淌的数据，这个**数据**其实就是我们常说的二进制数据，简单来说就是一大堆 **01 串**。纯裸TCP收发的这些 01 串之间是**没有任何边界**的，你根本不知道到哪个地方才算一条完整消息。

![图片](https://mmbiz.qpic.cn/mmbiz_png/AnAgeMhDIian70VibLxaCGiamEibMtALF3yp0uGvfiaBvibzvlWcYVvGdFz3RHvKtqUL32wW55gZXmZGBNQAYqRiamyHw/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)01二进制字节流

正因为这个没有**任何边界**的特点，所以当我们选择使用TCP发送"夏洛"和"特烦恼"**的时候，接收端收到的就是**"夏洛特烦恼"**，这时候接收端没发区分你是想要表达**"夏洛"+"特烦恼"**还是**"夏洛特"+"烦恼"。

![图片](https://mmbiz.qpic.cn/mmbiz_png/AnAgeMhDIian70VibLxaCGiamEibMtALF3ypeubStJG4qWVcRNoosE80VUjUjrxpfxoeawwUnNcZAO2KrpZrHSoHCQ/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)消息对比

这就是所谓的**粘包问题**，之前也写过一篇专门的[文章](https://mp.weixin.qq.com/s?__biz=Mzg5NDY2MDk4Mw==&mid=2247486377&idx=1&sn=bdc4b8b71559193b29aa0f54b95973db&scene=21#wechat_redirect)聊过这个问题。

说这个的目的是为了告诉大家，纯裸TCP是不能直接拿来用的，你需要在这个基础上加入一些**自定义的规则**，用于区分**消息边界**。

于是我们会把每条要发送的数据都包装一下，比如加入**消息头**，**消息头里写清楚一个完整的包长度是多少**，根据这个长度可以继续接收数据，截取出来后它们就是我们真正要传输的**消息体**。

![图片](https://mmbiz.qpic.cn/mmbiz_png/AnAgeMhDIian70VibLxaCGiamEibMtALF3ypLWrv8a3IzJ18ibKD6JrXH2n6rfyHu88VoBgMarKTOqFTcdPvKq3ADgw/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)消息边界长度标志

而这里头提到的**消息头**，还可以放各种东西，比如消息体是否被压缩过和消息体格式之类的，只要上下游都约定好了，互相都认就可以了，这就是所谓的**协议。**

每个使用TCP的项目都可能会定义一套类似这样的协议解析标准，他们可能**有区别，但原理都类似**。

**于是基于TCP，就衍生了非常多的协议，比如HTTP和RPC。**

## HTTP和RPC

我们回过头来看网络的分层图。

![图片](https://mmbiz.qpic.cn/mmbiz_png/AnAgeMhDIian70VibLxaCGiamEibMtALF3ypzTFY6J3Xl6ic6kibHm1cbF99qp1jUydcHruO7icqkQ1eyiaicNYSGIHEUibw/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)四层网络协议

**TCP是传输层的协议**，而基于TCP造出来的HTTP和**各类**RPC协议，它们都只是定义了不同消息格式的**应用层协议**而已。

**HTTP**协议（**H**yper **T**ext **T**ransfer **P**rotocol），又叫做**超文本传输协议**。我们用的比较多，平时上网在浏览器上敲个网址就能访问网页，这里用到的就是HTTP协议。

![图片](https://mmbiz.qpic.cn/mmbiz_png/AnAgeMhDIian70VibLxaCGiamEibMtALF3ypQJkVYrBjtMuxNxpiaZW8m6jR7prZgHMJbica6ScbGTer9OauNfJJTKRw/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)HTTP调用

而**RPC**（**R**emote **P**rocedure **C**all），又叫做**远程过程调用**。它本身并不是一个具体的协议，而是一种**调用方式**。

举个例子，我们平时调用一个**本地方法**就像下面这样。

```
 res = localFunc(req)
```

如果现在这不是个本地方法，而是个**远端服务器**暴露出来的一个方法`remoteFunc`，如果我们还能像调用本地方法那样去调用它，这样就可以**屏蔽掉一些网络细节**，用起来更方便，岂不美哉？

```
 res = remoteFunc(req)
```

![图片](https://mmbiz.qpic.cn/mmbiz_png/AnAgeMhDIian70VibLxaCGiamEibMtALF3ypPXG0cLR4oHqeicsysNiaWLAxTTTyT5hZIWXibZYDI3lcLBplYib1icMwMcA/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)RPC可以像调用本地方法那样调用远端方法

基于这个思路，大佬们造出了非常多款式的RPC协议，比如比较有名的`gRPC`，`thrift`。

值得注意的是，虽然大部分RPC协议底层使用TCP，但实际上**它们不一定非得使用TCP，改用UDP或者HTTP，其实也可以做到类似的功能。**

![图片](https://mmbiz.qpic.cn/mmbiz_png/AnAgeMhDIian70VibLxaCGiamEibMtALF3ypA7znyAkib06Et4EPuCxyibWGpajwud8cCndiaVkTYgZfPNmjmkNuSQeug/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)基于TCP协议的HTTP和RPC协议

到这里，我们回到文章标题的问题。

**既然有HTTP协议，为什么还要有RPC？**

其实，`TCP`是**70年**代出来的协议，而`HTTP`是**90年代**才开始流行的。而直接使用裸TCP会有问题，可想而知，这中间这么多年有多少自定义的协议，而这里面就有**80年代**出来的`RPC`。

所以我们该问的不是**既然有HTTP协议为什么要有RPC**，而是**为什么有RPC还要有HTTP协议**。

## 那既然有RPC了，为什么还要有HTTP呢？

现在电脑上装的各种**联网**软件，比如xx管家，xx卫士，它们都作为客户端（client）**需要跟**服务端（server）**建立连接收发消息，此时都会用到应用层协议，在这种**client/server (c/s)架构下，它们可以使用自家造的RPC协议，因为它只管连自己公司的服务器就ok了。

但有个软件不同，**浏览器（browser）**，不管是chrome还是IE，它们不仅要能访问自家公司的**服务器（server）**，还需要访问其他公司的网站服务器，因此它们需要有个统一的标准，不然大家没法交流。于是，HTTP就是那个时代用于统一 **browser/server (b/s)** 的协议。

也就是说在多年以前，**HTTP主要用于b/s架构，而RPC更多用于c/s架构。但现在其实已经没分那么清了，b/s和c/s在慢慢融合。\**很多软件同时支持多端，比如某度云盘，既要支持\**网页版**，还要支持**手机端和pc端**，如果通信协议都用HTTP的话，那服务器只用同一套就够了。而RPC就开始退居幕后，一般用于公司内部集群里，各个微服务之间的通讯。

那这么说的话，**都用HTTP得了，还用什么RPC？**

仿佛又回到了文章开头的样子，那这就要从它们之间的区别开始说起。

## HTTP和RPC有什么区别

我们来看看RPC和HTTP区别比较明显的几个点。

### **服务发现**

首先要向某个服务器发起请求，你得先建立连接，而建立连接的前提是，你得知道**IP地址和端口**。这个找到服务对应的IP端口的过程，其实就是**服务发现**。

在**HTTP**中，你知道服务的域名，就可以通过**DNS服务**去解析得到它背后的IP地址，默认80端口。

而**RPC**的话，就有些区别，一般会有专门的**中间服务**去保存服务名和IP信息，比如**consul或者etcd，甚至是redis**。想要访问某个服务，就去这些中间服务去获得IP和端口信息。由于dns也是服务发现的一种，所以也有基于dns去做服务发现的组件，比如**CoreDNS**。

可以看出服务发现这一块，两者是有些区别，但不太能分高低。

### **底层连接形式**

以主流的**HTTP1.1**协议为例，其默认在建立底层TCP连接之后会一直保持这个连接（**keep alive**），之后的请求和响应都会复用这条连接。

而**RPC**协议，也跟HTTP类似，也是通过建立TCP长链接进行数据交互，但不同的地方在于，RPC协议一般还会再建个**连接池**，在请求量大的时候，建立多条连接放在池内，要发数据的时候就从池里取一条连接出来，**用完放回去，下次再复用**，可以说非常环保。

![图片](https://mmbiz.qpic.cn/mmbiz_png/AnAgeMhDIian70VibLxaCGiamEibMtALF3ypEBlceCsCvPeLiabiakibB3djm4bK8ng3HLkmkSgjdFMicIAfWRUxvGTaZg/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)connection_pool

**由于连接池有利于提升网络请求性能，所以不少编程语言的网络库里都会给HTTP加个连接池**，比如**go**就是这么干的。

可以看出这一块两者也没太大区别，所以也不是关键。

### **传输的内容**

基于TCP传输的消息，说到底，无非都是**消息头header和消息体body。**

**header**是用于标记一些特殊信息，其中最重要的是**消息体长度**。

**body**则是放我们真正需要传输的内容，而这些内容只能是二进制01串，毕竟计算机只认识这玩意。所以TCP传字符串和数字都问题不大，因为字符串可以转成编码再变成01串，而数字本身也能直接转为二进制。但结构体呢，我们得想个办法将它也转为二进制01串，这样的方案现在也有很多现成的，比如**json，protobuf。**

这个将结构体转为二进制数组的过程就叫**序列化**，反过来将二进制数组复原成结构体的过程叫**反序列化**。

![图片](https://mmbiz.qpic.cn/mmbiz_png/AnAgeMhDIian70VibLxaCGiamEibMtALF3ypBmrwZSn2u8lCIvgNFJNaA1YBsDYoDjiahLLOVaTQG1EZpESzYlqUpgg/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)序列化和反序列化

对于主流的HTTP1.1，虽然它现在叫**超文本**协议，支持音频视频，但HTTP设计初是用于做网页**文本**展示的，所以它传的内容以字符串为主。header和body都是如此。在body这块，它使用**json**来**序列化**结构体数据。

我们可以随便截个图直观看下。

![图片](https://mmbiz.qpic.cn/mmbiz_png/AnAgeMhDIian70VibLxaCGiamEibMtALF3ypQDcBMBDHvc4gO7hZV8Kdbu7ko0Kd67g4o7GprkOzo2wa1AcKpPhzfQ/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)HTTP报文

可以看到这里面的内容非常多的**冗余**，显得**非常啰嗦**。最明显的，像`header`里的那些信息，其实如果我们约定好头部的第几位是content-type，就**不需要每次都真的把"content-type"这个字段都传过来**，类似的情况其实在`body`的json结构里也特别明显。

而RPC，因为它定制化程度更高，可以采用体积更小的protobuf或其他序列化协议去保存结构体数据，同时也不需要像HTTP那样考虑各种浏览器行为，比如302重定向跳转啥的。**因此性能也会更好一些，这也是在公司内部微服务中抛弃HTTP，选择使用RPC的最主要原因。**

![图片](https://mmbiz.qpic.cn/mmbiz_png/AnAgeMhDIian70VibLxaCGiamEibMtALF3yplbgyUCPhGSFb0V7Wnvl02eZkyKsgOVzdH038puXRWuGpCZXTRzS9Kg/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)HTTP原理

![图片](https://mmbiz.qpic.cn/mmbiz_png/AnAgeMhDIian70VibLxaCGiamEibMtALF3yp0VZ4Gnba01GJjBU7bfy9icxqeOthCL7jR7eAuUujaiaElOZOhKQnMpAw/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)RPC原理

当然上面说的HTTP，其实**特指的是现在主流使用的HTTP1.1**，`HTTP2`在前者的基础上做了很多改进，所以**性能可能比很多RPC协议还要好**，甚至连`gRPC`底层都直接用的`HTTP2`。

那么问题又来了。

## 为什么既然有了HTTP2，还要有RPC协议？

这个是由于HTTP2是2015年出来的。那时候很多公司内部的RPC协议都已经跑了好些年了，基于历史原因，一般也没必要去换了。

## 总结

- 纯裸TCP是能收发数据，但它是个**无边界**的数据流，上层需要定义**消息格式**用于定义**消息边界**。于是就有了各种协议，HTTP和各类RPC协议就是在TCP之上定义的应用层协议。
- **RPC本质上不算是协议，而是一种调用方式**，而像gRPC和thrift这样的具体实现，才是协议，它们是实现了RPC调用的协议。目的是希望程序员能像调用本地方法那样去调用远端的服务方法。同时RPC有很多种实现方式，**不一定非得基于TCP协议**。
- 从发展历史来说，HTTP主要用于b/s架构，而RPC更多用于c/s架构。但现在其实已经没分那么清了，b/s和c/s在慢慢融合。很多软件同时支持多端，所以对外一般用HTTP协议，而内部集群的微服务之间则采用RPC协议进行通讯。
- RPC其实比HTTP出现的要早，且比目前主流的HTTP1.1**性能**要更好，所以大部分公司内部都还在使用RPC。
- **HTTP2.0**在**HTTP1.1**的基础上做了优化，性能可能比很多RPC协议都要好，但由于是这几年才出来的，所以也不太可能取代掉RPC。

最后留个问题吧，大家有没有发现，不管是HTTP还是RPC，它们都有个特点，那就是消息都是客户端请求，服务端响应。**客户端没问，服务端肯定就不答**，这就有点僵了，但现实中肯定有需要**下游主动发送消息给上游**的场景，比如打个网页游戏，站在那啥也不操作，怪也会主动攻击我，这种情况该怎么办呢？

# 二、 TCP 和 UDP能共用一个端口吗？

之前有读者在字节面试的时候，被问到：**TCP 和 UDP 可以同时监听相同的端口吗？**

关于端口的知识点，还是挺多可以讲的，比如还可以牵扯到这几个问题：

- 多个 TCP 服务进程可以同时绑定同一个端口吗？
- 客户端的端口可以重复使用吗？
- 客户端 TCP 连接 TIME_WAIT 状态过多，会导致端口资源耗尽而无法建立新的连接吗？

所以，这次就跟大家盘一盘这些问题。

## **TCP 和 UDP 可以同时绑定相同的端口吗？**

其实我感觉这个问题「TCP 和 UDP 可以同时监听相同的端口吗？」表述有问题，这个问题应该表述成「**TCP 和 UDP 可以同时绑定相同的端口吗？**」

因为「监听」这个动作是在 TCP 服务端网络编程中才具有的，而 UDP 服务端网络编程中是没有「监听」这个动作的。

TCP 和 UDP 服务端网络相似的一个地方，就是会调用 bind 绑定端口。

给大家贴一下  TCP 和 UDP 网络编程的区别就知道了。

TCP 网络编程如下，服务端执行 listen() 系统调用就是监听端口的动作。

![图片](https://mmbiz.qpic.cn/mmbiz_png/J0g14CUwaZc0icWOnCWG3U7pUJlMQMKD3G3m50bM4icwe7LTVkfibL3Tkbiayv2KBgNiauN5uibQBmr82UaVyib5EroXg/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)TCP 网络编程

UDP 网络编程如下，服务端是没有监听这个动作的，只有执行  bind()  系统调用来绑定端口的动作。

![图片](https://mmbiz.qpic.cn/mmbiz_png/J0g14CUwaZc0icWOnCWG3U7pUJlMQMKD3NUrTxgvsKGOCVOray39icRZbKtq3kHpGS4YV8h9W7X5L7GibTaUQczjA/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)UDP 网络编程

> TCP 和 UDP 可以同时绑定相同的端口吗？

答案：**可以的**。

在数据链路层中，通过 MAC 地址来寻找局域网中的主机。在网际层中，通过 IP 地址来寻找网络中互连的主机或路由器。在传输层中，需要通过端口进行寻址，来识别同一计算机中同时通信的不同应用程序。

所以，传输层的「端口号」的作用，是为了区分同一个主机上不同应用程序的数据包。

传输层有两个传输协议分别是 TCP 和 UDP，在内核中是两个完全独立的软件模块。

当主机收到数据包后，可以在 IP 包头的「协议号」字段知道该数据包是 TCP/UDP，所以可以根据这个信息确定送给哪个模块（TCP/UDP）处理，送给 TCP/UDP 模块的报文根据「端口号」确定送给哪个应用程序处理。

![图片](https://mmbiz.qpic.cn/mmbiz_jpg/J0g14CUwaZc0icWOnCWG3U7pUJlMQMKD3q90yibcXrqNWPTcXAT62Jvdo9XyHJFK19uyh53IcTkiarQkrksd96D9w/640?wx_fmt=jpeg&wxfrom=5&wx_lazy=1&wx_co=1)

因此， TCP/UDP 各自的端口号也相互独立，如 TCP 有一个 80 号端口，UDP 也可以有一个 80 号端口，二者并不冲突。

> 验证结果

我简单写了 TCP 和 UDP 服务端的程序，它们都绑定同一个端口号 8888。

![图片](https://mmbiz.qpic.cn/mmbiz_png/J0g14CUwaZc0icWOnCWG3U7pUJlMQMKD3Wsnw83tMe5pecpouGS0nvrUtuvL0j5lQfWgNRAkD2F5bJyUMBOqia0Q/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

运行这两个程序后，通过 netstat 命令可以看到，TCP 和 UDP 是可以同时绑定同一个端口号的。

![图片](https://mmbiz.qpic.cn/mmbiz_png/J0g14CUwaZc0icWOnCWG3U7pUJlMQMKD39Qoqlb4wSFRzaHT2Rria3qUrpZLpJr7KUno0OQsEOWHTuLROgE9Kx8w/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

## **多个 TCP 服务进程可以绑定同一个端口吗？**

还是以前面的 TCP 服务端程序作为例子，启动两个同时绑定同一个端口的 TCP 服务进程。

运行第一个  TCP 服务进程之后，netstat 命令可以查看，8888 端口已经被一个 TCP 服务进程绑定并监听了，如下图：

![图片](https://mmbiz.qpic.cn/mmbiz_png/J0g14CUwaZc0icWOnCWG3U7pUJlMQMKD34KDd4YKOvX4XibSoNqQEMqBUPiah5oaesWKDCXiaBtm550UpWUCNAQHNw/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

接着，运行第二个 TCP 服务进程的时候，就报错了“Address already in use”，如下图：

![图片](https://mmbiz.qpic.cn/mmbiz_png/J0g14CUwaZc0icWOnCWG3U7pUJlMQMKD3f6rfokdqiaUuYy4UXlzZ9C5Qwib3g9NYHu2maLpCZ9TGSXm3GE0ZQ3fw/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

我上面的测试案例是两个 TCP 服务进程同时绑定地址和端口是：0.0.0.0 地址和8888端口，所以才出现的错误。

如果两个 TCP 服务进程绑定的 IP 地址不同，而端口相同的话，也是可以绑定成功的，如下图：

![图片](https://mmbiz.qpic.cn/mmbiz_png/J0g14CUwaZc0icWOnCWG3U7pUJlMQMKD35qWKU7QUxlUxibpcQ7RiaarTS1MD0g8rEwUB6sN0RgrJEHy5pxCxYNeA/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

所以，默认情况下，针对「多个 TCP 服务进程可以绑定同一个端口吗？」这个问题的答案是：**如果两个 TCP 服务进程同时绑定的 IP 地址和端口都相同，那么执行 bind() 时候就会出错，错误是“Address already in use”**。

注意，如果 TCP 服务进程 A 绑定的地址是  0.0.0.0 和端口 8888，而如果 TCP 服务进程 B 绑定的地址是 192.168.1.100 地址（或者其他地址）和端口 8888，那么执行 bind() 时候也会出错。

这是因为 0.0.0.0  地址比较特殊，代表任意地址，意味着绑定了 0.0.0.0  地址，相当于把主机上的所有 IP 地址都绑定了。

> 重启 TCP 服务进程时，为什么会有“Address in use”的报错信息？

TCP 服务进程需要绑定一个 IP 地址和一个端口，然后就监听在这个地址和端口上，等待客户端连接的到来。

然后在实践中，我们可能会经常碰到一个问题，当 TCP 服务进程重启之后，总是碰到“Address in use”的报错信息，TCP 服务进程不能很快地重启，而是要过一会才能重启成功。

这是为什么呢？

当我们重启 TCP 服务进程的时候，意味着通过服务器端发起了关闭连接操作，于是就会经过四次挥手，而对于主动关闭方，会在 TIME_WAIT 这个状态里停留一段时间，这个时间大约为 2MSL。

![图片](https://mmbiz.qpic.cn/mmbiz_png/J0g14CUwaZc0icWOnCWG3U7pUJlMQMKD39PFg1YXcic7htqddSLrwezDgaHIcvAyib9Xj6dLmST6MhvVAmw5TooLA/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

**当 TCP 服务进程重启时，服务端会出现 TIME_WAIT 状态的连接，TIME_WAIT 状态的连接使用的 IP+PORT 仍然被认为是一个有效的 IP+PORT 组合，相同机器上不能够在该 IP+PORT 组合上进行绑定，那么执行 bind() 函数的时候，就会返回了 Address already in use 的错误**。

而等 TIME_WAIT 状态的连接结束后，重启 TCP 服务进程就能成功。

> 重启 TCP 服务进程时，如何避免“Address in use”的报错信息？

我们可以在调用 bind 前，对 socket 设置 SO_REUSEADDR 属性，可以解决这个问题。



```
int on = 1;
setsockopt(listenfd, SOL_SOCKET, SO_REUSEADDR, &on, sizeof(on));
```

因为 SO_REUSEADDR 作用是**：如果当前启动进程绑定的 IP+PORT 与处于TIME_WAIT 状态的连接占用的 IP+PORT 存在冲突，但是新启动的进程使用了 SO_REUSEADDR 选项，那么该进程就可以绑定成功。**

举个例子，服务端有个监听 0.0.0.0 地址和 8888 端口的 TCP 服务进程。‍

![图片](https://mmbiz.qpic.cn/mmbiz_png/J0g14CUwaZcY2C2lgEVXPwZWrtoP4MwEXUSa804Jx1dZmNibuqsZ4INxJ7yPibep5HknjnfG3icdbBDEHBGy7jkzA/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

有个客户端（IP地址：192.168.1.100）已经和服务端（IP 地址：172.19.11.200）建立了 TCP 连接，那么在 TCP 服务进程重启时，服务端会与客户端经历四次挥手，服务端的 TCP 连接会短暂处于 TIME_WAIT 状态：

```
客户端地址:端口           服务端地址:端口        TCP 连接状态
192.168.1.100:37272     172.19.11.200:8888    TIME_WAIT
```

如果 TCP 服务进程没有对 socket 设置 SO_REUSEADDR 属性，那么在重启时，由于存在一个和绑定 IP+PORT 一样的 TIME_WAIT 状态的连接，那么在执行 bind() 函数的时候，就会返回了 Address already in use 的错误。

如果 TCP 服务进程对 socket 设置 SO_REUSEADDR 属性了，那么在重启时，即使存在一个和绑定 IP+PORT 一样的 TIME_WAIT 状态的连接，依然可以正常绑定成功，因此可以正常重启成功。

因此，在所有 TCP 服务器程序中，调用 bind 之前最好对 socket 设置 SO_REUSEADDR 属性，这不会产生危害，相反，它会帮助我们在很快时间内重启服务端程序。‍

**前面我提到过这个问题：**如果 TCP 服务进程 A 绑定的地址是  0.0.0.0 和端口 8888，而如果 TCP 服务进程 B 绑定的地址是 192.168.1.100 地址（或者其他地址）和端口 8888，那么执行 bind() 时候也会出错。

这个问题也可以由 SO_REUSEADDR 解决，因为它的**另外一个作用是：****绑定的 IP地址 + 端口时，只要 IP 地址不是正好(exactly)相同，那么允许绑定。**

比如，0.0.0.0:8888 和192.168.1.100:8888，虽然逻辑意义上前者包含了后者，但是 0.0.0.0 泛指所有本地 IP，而 192.168.1.100 特指某一IP，两者并不是完全相同，所以在对 socket 设置 SO_REUSEADDR 属性后，那么执行 bind() 时候就会绑定成功。

## **客户端的端口可以重复使用吗？**

客户端在执行 connect 函数的时候，会在内核里随机选择一个端口，然后向服务端发起 SYN 报文，然后与服务端进行三次握手。

![图片](https://mmbiz.qpic.cn/mmbiz_png/J0g14CUwaZc0icWOnCWG3U7pUJlMQMKD3mwHryZ5MpTnA0pLIk0CzYLaDjcnJIYIMhQlYrWgPFDl7Wu9avr5aSA/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

所以，客户端的端口选择的发生在 connect 函数，内核在选择端口的时候，会从 `net.ipv4.ip_local_port_range` 这个内核参数指定的范围来选取一个端口作为客户端端口。

该参数的默认值是 32768 61000，意味着端口总可用的数量是 61000 - 32768 = 28232 个。

当客户端与服务端完成 TCP 连接建立后，我们可以通过 netstat 命令查看 TCP 连接。

```
$ netstat -napt
协议  源ip地址:端口            目的ip地址：端口         状态
tcp  192.168.110.182.64992   117.147.199.51.443     ESTABLISHED
```

> 那问题来了，上面客户端已经用了 64992 端口，那么还可以继续使用该端口发起连接吗？

这个问题，很多同学都会说不可以继续使用该端口了，如果按这个理解的话， 默认情况下客户端可以选择的端口是 28232 个，那么意味着客户端只能最多建立  28232 个 TCP 连接，如果真是这样的话，那么这个客户端并发连接也太少了吧，所以这是错误理解。

正确的理解是，**TCP 连接是由四元组（源IP地址，源端口，目的IP地址，目的端口）唯一确认的，那么只要四元组中其中一个元素发生了变化，那么就表示不同的 TCP 连接的。所以如果客户端已使用端口 64992 与服务端 A 建立了连接，那么客户端要与服务端 B 建立连接，还是可以使用端口 64992 的，因为内核是通过四元祖信息来定位一个 TCP 连接的，并不会因为客户端的端口号相同，而导致连接冲突的问题。**

比如下面这张图，有 2 个 TCP 连接，左边是客户端，右边是服务端，客户端使用了相同的端口 50004 与两个服务端建立了 TCP 连接。

![图片](https://mmbiz.qpic.cn/mmbiz_jpg/J0g14CUwaZc0icWOnCWG3U7pUJlMQMKD3wjcrvyEbsUMYLqNQIBjBSwXu5ltl4yukC5xc6Qv1SibAHhZABm6Nkug/640?wx_fmt=jpeg&wxfrom=5&wx_lazy=1&wx_co=1)

仔细看，上面这两条 TCP 连接的四元组信息中的「目的 IP 地址」是不同的，一个是 180.101.49.12 ，另外一个是 180.101.49.11。

> 多个客户端可以 bind 同一个端口吗？

bind 函数虽然常用于服务端网络编程中，但是它也是用于客户端的。

前面我们知道，客户端是在调用 connect 函数的时候，由内核随机选取一个端口作为连接的端口。

而如果我们想自己指定连接的端口，就可以用 bind 函数来实现：客户端先通过 bind 函数绑定一个端口，然后调用 connect 函数就会跳过端口选择的过程了，转而使用 bind 时确定的端口。

针对这个问题：多个客户端可以 bind 同一个端口吗？

要看多个客户端绑定的 IP + PORT 是否都相同，如果都是相同的，那么在执行 bind() 时候就会出错，错误是“Address already in use”。

如果一个绑定在 192.168.1.100:6666，一个绑定在 192.168.1.200:6666，因为 IP 不相同，所以执行 bind() 的时候，能正常绑定。

所以， 如果多个客户端同时绑定的 IP 地址和端口都是相同的，那么执行 bind() 时候就会出错，错误是“Address already in use”。

一般而言，客户端不建议使用 bind 函数，应该交由 connect 函数来选择端口会比较好，因为客户端的端口通常都没什么意义。

> 客户端 TCP 连接 TIME_WAIT 状态过多，会导致端口资源耗尽而无法建立新的连接吗？

针对这个问题要看，客户端是否都是与同一个服务器（目标地址和目标端口一样）建立连接。

如果客户端都是与同一个服务器（目标地址和目标端口一样）建立连接，那么如果客户端 TIME_WAIT 状态的连接过多，当端口资源被耗尽，就无法与这个服务器再建立连接了。

但是，**因为只要客户端连接的服务器不同，端口资源可以重复使用的**。

所以，如果客户端都是与不同的服务器建立连接，即使客户端端口资源只有几万个， 客户端发起百万级连接也是没问题的（当然这个过程还会受限于其他资源，比如文件描述符、内存、CPU 等）。

> 如何解决客户端 TCP 连接 TIME_WAIT 过多，导致无法与同一个服务器建立连接的问题？

前面我们提到，如果客户端都是与同一个服务器（目标地址和目标端口一样）建立连接，那么如果客户端 TIME_WAIT 状态的连接过多，当端口资源被耗尽，就无法与这个服务器再建立连接了。

针对这个问题，也是有解决办法的，那就是打开 `net.ipv4.tcp_tw_reuse` 这个内核参数。

**因为开启了这个内核参数后，客户端调用 connect  函数时，如果选择到的端口，已经被相同四元组的连接占用的时候，就会判断该连接是否处于  TIME_WAIT 状态，如果该连接处于 TIME_WAIT 状态并且 TIME_WAIT 状态持续的时间超过了 1 秒，那么就会重用这个连接，然后就可以正常使用该端口了。**

举个例子，假设客户端已经与服务器建立了一个 TCP 连接，并且这个状态处于  TIME_WAIT 状态：

```
客户端地址:端口           服务端地址:端口         TCP 连接状态
192.168.1.100:2222      172.19.11.21:8888     TIME_WAIT
```

然后客户端又与该服务器（172.19.11.21:8888）发起了连接，**在调用 connect 函数时，内核刚好选择了 2222 端口，接着发现已经被相同四元组的连接占用了：**

- 如果**没有开启** net.ipv4.tcp_tw_reuse  内核参数，那么内核就会选择下一个端口，然后继续判断，直到找到一个没有被相同四元组的连接使用的端口， 如果端口资源耗尽还是没找到，那么 connect 函数就会返回错误。
- 如果**开启**了 net.ipv4.tcp_tw_reuse  内核参数，就会判断该四元组的连接状态是否处于 TIME_WAIT 状态，**如果连接处于 TIME_WAIT 状态并且该状态持续的时间超过了 1 秒，那么就会重用该连接**，于是就可以使用 2222 端口了，这时 connect 就会返回成功。

再次提醒一次，开启了 net.ipv4.tcp_tw_reuse  内核参数，是客户端（连接发起方） 在调用 connect() 函数时才起作用，所以在服务端开启这个参数是没有效果的。

> 客户端端口选择的流程总结

至此，我们已经把客户端在执行 connect 函数时，内核选择端口的情况大致说了一遍，为了让大家更明白客户端端口的选择过程，我画了一流程图。

![图片](https://mmbiz.qpic.cn/mmbiz_jpg/J0g14CUwaZc0icWOnCWG3U7pUJlMQMKD38NYRqlicOHcfMArRXJyoNGsfUB06PcqKjxwkVwxZeAhkUwEmx7aLLVg/640?wx_fmt=jpeg&wxfrom=5&wx_lazy=1&wx_co=1)

## **总结**

> TCP 和 UDP 可以同时绑定相同的端口吗？

可以的。

TCP 和 UDP 传输协议，在内核中是由两个完全独立的软件模块实现的。

当主机收到数据包后，可以在 IP 包头的「协议号」字段知道该数据包是 TCP/UDP，所以可以根据这个信息确定送给哪个模块（TCP/UDP）处理，送给 TCP/UDP 模块的报文根据「端口号」确定送给哪个应用程序处理。

因此， TCP/UDP 各自的端口号也相互独立，互不影响。

> 多个 TCP 服务进程可以同时绑定同一个端口吗？

如果两个 TCP 服务进程同时绑定的 IP 地址和端口都相同，那么执行 bind() 时候就会出错，错误是“Address already in use”。

如果两个 TCP 服务进程绑定的端口都相同，而 IP 地址不同，那么执行 bind() 不会出错。

> 如何解决服务端重启时，报错“Address already in use”的问题？

当我们重启 TCP 服务进程的时候，意味着通过服务器端发起了关闭连接操作，于是就会经过四次挥手，而对于主动关闭方，会在 TIME_WAIT 这个状态里停留一段时间，这个时间大约为 2MSL。

当 TCP 服务进程重启时，服务端会出现 TIME_WAIT 状态的连接，TIME_WAIT 状态的连接使用的 IP+PORT 仍然被认为是一个有效的 IP+PORT 组合，相同机器上不能够在该 IP+PORT 组合上进行绑定，那么执行 bind() 函数的时候，就会返回了 Address already in use 的错误。

要解决这个问题，我们可以对 socket 设置 SO_REUSEADDR 属性。

这样即使存在一个和绑定 IP+PORT 一样的 TIME_WAIT 状态的连接，依然可以正常绑定成功，因此可以正常重启成功。

> 客户端的端口可以重复使用吗？

在客户端执行 connect 函数的时候，只要客户端连接的服务器不是同一个，内核允许端口重复使用。

TCP 连接是由四元组（源IP地址，源端口，目的IP地址，目的端口）唯一确认的，那么只要四元组中其中一个元素发生了变化，那么就表示不同的 TCP 连接的。

所以，如果客户端已使用端口 64992 与服务端 A 建立了连接，那么客户端要与服务端 B 建立连接，还是可以使用端口 64992 的，因为内核是通过四元祖信息来定位一个 TCP 连接的，并不会因为客户端的端口号相同，而导致连接冲突的问题。

> 客户端 TCP 连接 TIME_WAIT 状态过多，会导致端口资源耗尽而无法建立新的连接吗？

要看客户端是否都是与同一个服务器（目标地址和目标端口一样）建立连接。

如果客户端都是与同一个服务器（目标地址和目标端口一样）建立连接，那么如果客户端 TIME_WAIT 状态的连接过多，当端口资源被耗尽，就无法与这个服务器再建立连接了。即使在这种状态下，还是可以与其他服务器建立连接的，只要客户端连接的服务器不是同一个，那么端口是重复使用的。

> 如何解决客户端 TCP 连接 TIME_WAIT 过多，导致无法与同一个服务器建立连接的问题？

打开 net.ipv4.tcp_tw_reuse  这个内核参数。

因为开启了这个内核参数后，客户端调用 connect  函数时，如果选择到的端口，已经被相同四元组的连接占用的时候，就会判断该连接是否处于  TIME_WAIT 状态。

如果该连接处于 TIME_WAIT 状态并且 TIME_WAIT 状态持续的时间超过了 1 秒，那么就会重用这个连接，然后就可以正常使用该端口了。

完，搞定！![图片](https://mmbiz.qpic.cn/mmbiz_png/J0g14CUwaZc0icWOnCWG3U7pUJlMQMKD3eXCcD685vMvlIuy4z4xFo9ia1CPkbpAqyIicuXiaHHicCs5FAITElPYQjQ/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

# 三、HTTP/3 ，它来了！

带大家看看 HTTP 3.0。

HTTP 3.0 是 HTTP 协议的第三个主要版本，前两个分别是 HTTP 1.0 和 HTTP 2.0 ，但其实 HTTP 1.1 我认为才是真正的 HTTP 1.0。

我们大家知道，HTTP 是应用层协议，应用层产生的数据会通过传输层协议作为载体来传输到互联网上的其他主机中，而其中的载体就是 TCP 协议，这是 HTTP 2 之前的主流模式。

但是随着 TCP 协议的缺点不断暴露出来，新一代的 HTTP 协议 - HTTP 3.0 毅然决然切断了和 TCP 的联系，转而拥抱了 UDP 协议，这么说不太准确，其实 HTTP 3.0 其实是拥抱了 **QUIC 协议**，而 QUIC 协议是建立在 UDP 协议基础上的。

## HTTP 3.0

HTTP 3.0 于 2022 年 6 月 6 日正式发布，IETF 把 HTTP 3.0 标准制定在了 RFC 9114 中，HTTP 3.0 其实相较于 HTTP 2.0 要比 HTTP 2.0 相较于 HTTP 1.1 的变化来说小很多，最大的提升就在于效率，替换 TCP 协议为 UDP 协议，HTTP 3.0 具有更低的延迟，它的效率甚至要比 HTTP 1.1 快 3 倍以上。

其实每一代 HTTP 协议的不断发展都是建立在上一代 HTTP 的缺点上的，就比如 HTTP 1.0 最大的问题就是传输安全性和不支持持久连接上，针对此出现了 HTTP 1.1 ，引入了 Keep-Alive 机制来保持长链接和 TLS 来保证通信安全性。但此时的 HTTP 协议并发性还做的不够好。

随着网络的不断发展，每个网站所需资源（CSS、JavaScript、图像等）的数量逐年增加，浏览器发现自己在获取和呈现网页时需要越来越多的并发性。但是由于 HTTP 1.1 只能够允许客户端/服务器进行一次 HTTP 请求交换，因此在网络层获得并发性的唯一方法是并行使用多个 TCP 连接到同一个源，不过使用多个 TCP 链接就失去了  keep-Alive 的意义。

然后出现了 *SPDY* 协议，主要解决 HTTP 1.1 效率不高的问题，包括降低延迟，压缩 header 等等，这些已经被 Chrome 浏览器证明能够产生优化效果，后来 HTTP 2.0 基于 SPDY ，并且引入了 **流( Stream )**的概念，它允许将不同的 HTTP 交换多路复用到同一个 TCP 连接上，从而达到让浏览器重用 TCP 链接的目的。

![图片](https://mmbiz.qpic.cn/mmbiz_png/A3ibcic1Xe0iaQduVCTmibd8Z8SmdIr86YCoqogAOjQHQMAfK4ZibT0PNpFJsLH1OKaicDha3mZxF2AaTHhH3YzibYhyA/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

TCP 的主要作用是以正确的顺序将整个字节流从一个端点传输到另一个端点，但是当流中的某些数据包丢失时，TCP 需要重新发送这些丢失的数据包，等到丢失的数据包到达对应端点时才能够被 HTTP 处理，这被称为 TCP 队头阻塞问题。

那么可能就会有人考虑到去修改 TCP 协议，其实这已经是一件不可能完成的任务了。因为 TCP 存在的时间实在太长，已经充斥在各种设备中，并且这个协议是由操作系统实现的，更新起来不大现实。

基于这个原因，**Google 就更起炉灶搞了一个基于 UDP 协议的 QUIC 协议，并且使用在了 HTTP/3 上**，HTTP/3 之前名为 HTTP-over-QUIC，从这个名字中我们也可以发现，HTTP/3 最大的改造就是使用了 QUIC。

![图片](https://mmbiz.qpic.cn/mmbiz_png/A3ibcic1Xe0iaQduVCTmibd8Z8SmdIr86YCoAaW5licEgIicVbibptTwFxFJapQYoicKfKnRk5x7ficE7jv0KGicA6AejRvQ/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

## QUIC 协议

QUIC 的小写是 quic，谐音 quick，意思就是`快`。它是 Google 提出来的一个基于 UDP 的传输协议，所以 QUIC 又被叫做**快速 UDP 互联网连接**。

首先 QUIC 的第一个特征就是快，为什么说它快，它到底快在哪呢？

我们大家知道，HTTP 协议在传输层是使用了 TCP 进行报文传输，而且 HTTPS 、HTTP/2.0 还采用了 TLS 协议进行加密，这样就会导致三次握手的连接延迟：即 TCP 三次握手（一次）和 TLS 握手（两次），如下图所示。

![图片](https://mmbiz.qpic.cn/mmbiz_jpg/A3ibcic1Xe0iaQduVCTmibd8Z8SmdIr86YCoQHoaLiaHXx1PAbn6FkAIVFSdx4qgo6zozDWgCCOOpDStIibVibCOQytxQ/640?wx_fmt=jpeg&wxfrom=5&wx_lazy=1&wx_co=1)

对于很多短连接场景，这种握手延迟影响较大，而且无法消除。毕竟 RTT 是人类和效率的终极斗争。

相比之下，QUIC 的握手连接更快，因为它使用了 UDP 作为传输层协议，这样能够减少三次握手的时间延迟。而且 QUIC 的加密协议采用了 TLS 协议的最新版本 *TLS 1.3*，相对之前的 *TLS 1.1-1.2*，TLS1.3 允许客户端无需等待 TLS 握手完成就开始发送应用程序数据的操作，可以支持1 RTT 和 0 RTT，从而达到**快速建立连接**的效果。

我们上面还说过，HTTP/2.0 虽然解决了队头阻塞问题，但是其建立的连接还是基于 TCP，无法解决请求阻塞问题。

而 UDP 本身没有建立连接这个概念，并且 QUIC 使用的 stream 之间是相互隔离的，不会阻塞其他 stream 数据的处理，所以使用 UDP 并不会造成队头阻塞。

在 TCP 中，TCP 为了保证数据的可靠性，使用了**序号+确认号**机制来实现，一旦带有 synchronize sequence number 的包发送到服务器，服务器都会在一定时间内进行响应，如果过了这段时间没有响应，客户端就会重传这个包，直到服务器收到数据包并作出响应为止。

> 那么 TCP 是如何判断它的重传超时时间呢？

TCP 一般采用的是**自适应重传算法**，这个超时时间会根据往返时间 RTT 动态调整的。每次客户端都会使用相同的 syn 来判断超时时间，导致这个 RTT 的结果计算的不太准确。

虽然 QUIC 没有使用 TCP 协议，但是它也保证了可靠性，QUIC 实现可靠性的机制是使用了 *Packet Number*，这个序列号可以认为是 synchronize  sequence number 的替代者，这个序列号也是递增的。与 syn 所不同的是，不管服务器有没有接收到数据包，这个 Packet Number 都会 + 1，而 syn 是只有服务器发送 ack 响应之后，syn 才会 + 1。

![图片](https://mmbiz.qpic.cn/mmbiz_jpg/A3ibcic1Xe0iaQduVCTmibd8Z8SmdIr86YColVuUvWicBuI2goMnmZ0CJdSD0oqlzIV3Nriad8TG6PfORiaP2BrABDicNQ/640?wx_fmt=jpeg&wxfrom=5&wx_lazy=1&wx_co=1)

比如有一个 PN = 10 的数据包在发送的过程中由于某些原因迟迟没到服务器，那么客户端会重传一个 PN = 11 的数据包，经过一段时间后客户端收到 PN = 10 的响应后再回送响应报文，此时的 RTT 就是 PN = 10 这个数据包在网络中的生存时间，这样计算相对比较准确。

> 虽然 QUIC 保证了数据包的可靠性，但是数据的可靠性是如何保证的呢？

QUIC 引入了一个 *stream offset* 的概念，一个 stream 可以传输多个 stream offset，每个 stream offset 其实就是一个 PN 标识的数据，即使某个 PN 标识的数据丢失，PN + 1 后，它重传的仍旧是 PN 所标识的数据，等到所有 PN 标识的数据发送到服务器，就会进行重组，以此来保证数据可靠性。到达服务器的 stream offset 会按照顺序进行组装，这同时也保证了数据的顺序性。

![图片](https://mmbiz.qpic.cn/mmbiz_jpg/A3ibcic1Xe0iaQduVCTmibd8Z8SmdIr86YCoHbeBkFictr4HJZU8MRtyB1GnpUzdy6OmZO1cEBGYEnIlZmG5noIwevA/640?wx_fmt=jpeg&wxfrom=5&wx_lazy=1&wx_co=1)

众所周知，TCP 协议的具体实现是由操作系统内核来完成的，应用程序只能使用，不能对内核进行修改，随着移动端和越来越多的设备接入互联网，性能逐渐成为一个非常重要的衡量指标。虽然移动网络发展的非常快，但是用户端的更新却非常缓慢，我仍然看见有很多地区很多计算机还仍旧使用 xp 系统，尽管它早已发展了很多年。服务端系统不依赖用户升级，但是由于操作系统升级涉及到底层软件和运行库的更新，所以也比较保守和缓慢。

QUIC 协议的一个重要特点就是**可插拔性**，能够动态更新和升级，QUIC 在应用层实现了拥塞控制算法，不需要操作系统和内核的支持，遇到拥塞控制算法切换时，只需要在服务器重新加载一边即可，不需要停机和重启。

我们知道 TCP 的流量控制是通过**滑动窗口**来实现的

而 QUIC 也实现了流量控制，QUIC 的流量控制也是使用了窗口更新 *window_update*，来告诉对端它可以接受的字节数。

TCP 协议头部没有经过加密和认证，所以在传输的过程中很可能被篡改，与之不同的是，QUIC 中的报文头部都是经过认证，报文也经过加密处理。这样只要对 QUIC 的报文有任何修改，接收端都能够及时发现，保证了安全性。

总的来说，QUIC 具有下面这些优势

- 使用 UDP 协议，不需要三次连接进行握手，而且也会缩短 TLS 建立连接的时间。
- 解决了队头阻塞问题。
- 实现动态可插拔，在应用层实现了拥塞控制算法，可以随时切换。
- 报文头和报文体分别进行认证和加密处理，保障安全性。
- 连接能够平滑迁移。

连接平滑迁移指的是，你的手机或者移动设备在 4G 信号下和 WiFi 等网络情况下切换，不会断线重连，用户甚至无任何感知，能够直接实现平滑的信号切换。

QUCI 协议已经被写在了 RFC 9000 中。



# 四、前端学习 HTTP ，看这篇就够了 !!

## **HTTP 起源**

`HTTP`是由蒂姆·伯纳斯-李（`TimBerners—Lee`）于1989年在欧洲核子研究组织（`CERN`）所发起

其中最著名的是 1999 年 6 月公布的**RFC 2616**[1]，定义了`HTTP`协议中现今广泛使用的一个版本——`HTTP 1.1`

![图片](https://mmbiz.qpic.cn/mmbiz_png/betIP9fVPicP9mOWh8f5hp8VpYXOEAAvNoKHfCsRugWcCZBmt9sgWngfWJG6v5TQFJGNQ1RDNdacfbUM8cfyTKA/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

## **HTTP 是什么**

全称：超文本传输协议（`HyperText Transfer Protocol`）

概念：`HTTP`是一种能够获取像`HTML`、图片等网络资源的通讯协议（`protocol`）。它是在`web`上进行数据交换的基础，是一种`client-server`协议

`HTTP`——因特网的多媒体信使 ——《HTTP权威指南》。`HTTP`在因特网的角色：充当一个信使的角色，干的就是一个跑腿的活，在客户端和服务端之间传递信息，但我们又不能缺少它。`HTTP`协议是应用层的协议，是与前端开发最息息相关的协议。平时我们遇到的`HTTP`请求、`HTTP`缓存、`Cookies`、跨域等其实都跟`HTTP`息息相关

## **HTTP 的基础特性**

- 可拓展协议。`HTTP 1.0`出现的`HTTP headers`让协议拓展变得更加的容易。只要服务端和客户端就`headers`达成语义一致，新功能就可以被轻松的加入进来
- `HTTP`是无状态的、有会话的。在同一个连接中，两个执行成功的`HTTP`请求之间是没有关系的。这就带来了一个问题，用户没有办法在同一个网站中进行连续的交互，比如在一个电商网站里，用户把某个商品加入到购物车，切换一个页面后再次添加了商品，这两次添加商品的请求之间没有关联，浏览器无法知道用户最终选择了哪些商品。而使用`HTTP`的头部扩展，`HTTP Cookies`就可以解决这个问题。把`Cookies`添加到头部中，创建一个会话让每次请求都能共享相同的上下文信息，达成相同的状态。
- `HTTP`与连接。通过`TCP`，或者`TLS`——加密的`TCP`连接来发送，理论上任何可靠的传输协议都可以使用。连接是传输层控制的，这从根本上来讲不是`HTTP`的范畴。

![图片](https://mmbiz.qpic.cn/mmbiz_png/betIP9fVPicP9mOWh8f5hp8VpYXOEAAvN3ibffK8frUExPhos5xWZDOyx9QZ9XxUdTibf95uoEF8X9Moa1NeUBmicw/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

也就是说，`HTTP`依赖于面向连接的`TCP`进行消息传递，但连接并不是必须的。只需要它是可靠的，或不丢失消息的（至少返回错误）。

`HTTP/1.0`默认为每一对`HTTP`请求/响应都打开一个单独的`TCP`连接。当需要连续发起多个请求时，这种模式比多个请求共享同一个`TCP`链接更低效。为此，`HTTP 1.1`持久连接的概念，底层`TCP`连接可以通过`connection`头部实现。但`HTTP 1.1`在连接上也是不完美的，后面我们会提到。

## **基于 HTTP 的组件系统**

`HTTP`的组件系统包括客户端、`web`服务器和代理

![图片](https://mmbiz.qpic.cn/mmbiz_png/betIP9fVPicP9mOWh8f5hp8VpYXOEAAvNXVlfJFhDX9IJHrcMPj9zhvNicO6Zk2tOWymqLHrAGEUKI5lvKz8WsNw/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)





### 客户端：user-agent

浏览器，特殊比如是工程师使用的程序，以及`Web`开发人员调试应用程序

### Web服务端

由`Web Server`来服务并提供客户端所请求的文档。每一个发送到服务器的请求，都会被服务器处理并返回一个消息，也就是`response`

### 代理（Proxies）

在浏览器和服务器之间，有很多计算机和其他设备转发了`HTTP`消息。它们可能出现在传输层、网络层和物理层上，对于`HTTP`应用层而言就是透明的

有如下的一些作用

- 缓存
- 过滤（像防病毒扫描、家长控制）
- 负载均衡
- 认证（对不同的资源进行权限控制）
- 日志管理

## **HTTP 报文组成**

HTTP 有两种类型的消息：

- 请求——由客户端发送用来触发一个服务器上的动作
- 响应——来自服务器端的应答

`HTTP`消息由采用`ASCII`编码的多行文本构成的。在`HTTP/1.1`以及更早的版本中，这些消息通过连接公开的发送。在`HTTP2.0`中，消息被分到了多个`HTTP`帧中。通过配置文件（用于代理服务器或者服务器），`API`（用于浏览器）或者其他接口提供`HTTP`消息

### 典型的 HTTP 会话

- 建立连接      在客户端-服务器协议中，连接是由客户端发起建立的。在`HTTP`中打开连接意味着在底层传输层启动连接，通常是`TCP`。使用`TCP`时，`HTTP`服务器的默认端口号是`80`，另外还有`8000`和`8080`也很常用
- 发送客户端请求
- 服务器响应请求

### HTTP 请求和响应

HTTP 请求和响应都包括起始行（`start line`）、请求头（`HTTP Headers`）、空行（`empty line`）以及`body`部分，如下图所示：

![图片](https://mmbiz.qpic.cn/mmbiz_png/betIP9fVPicP9mOWh8f5hp8VpYXOEAAvNgVcSwlONtRwsv3RVibzg9BmF7jg2VdT39ibEp0ZRAsqsCTP5BU9rHSlA/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)



- 起始行。请求的起始行：请求方法、请求`Path`和`HTTP`版本号     响应的起始行：`HTTP`版本号、响应状态码以及状态文本描述

下面详细说下请求`Path`，请求路径（`Path`）有以下几种：

1）一个绝对路径，末尾跟上一个 ' ? ' 和查询字符串。这是最常见的形式，称为 原始形式 (`origin form`)，被`GET`，`POST`，`HEAD`和`OPTIONS`方法所使用

```
POST / HTTP/1.1
GET /background.png HTTP/1.0
HEAD /test.html?query=alibaba HTTP/1.1
OPTIONS /anypage.html HTTP/1.0
```

2）一个完整的`URL`。主要在使用`GET`方法连接到代理的时候使用

```
GET http://developer.mozilla.org/en-US/docs/Web/HTTP/Messages HTTP/1.1
```

3）由域名和可选端口（以':'为前缀）组成的`URL`的`authority component`，称为`authority form`。仅在使用`CONNECT`建立`HTTP`隧道时才使用

```
CONNECT developer.mozilla.org:80 HTTP/1.1
```

4）星号形式 (`asterisk form`)，一个简单的星号('*')，配合`OPTIONS`方法使用，代表整个服务器。

```
OPTIONS * HTTP/1.1
```

- `Headers`请求头或者响应头。详见下面的首部。不区分大小写的字符串，紧跟着的冒号 (':') 和一个结构取决于`header`的值
- 空行。很多人容易忽略
- `Body`

请求`Body`部分：有些请求将数据发送到服务器以便更新数据：常见的的情况是`POST`请求（包含`HTML`表单数据）。请求报文的`Body`一般为两类。一类是通过`Content-Type`和`Content-Length`定义的单文件`body`。另外一类是由多`Body`组成，通常是和`HTML Form`联系在一起的。两者的不同表现在于`Content-Type`的值。

1）`Content-Type —— application/x-www-form-urlencoded`对于`application/x-www-form-urlencoded`格式的表单内容，有以下特点:

I.其中的数据会被编码成以&分隔的键值对

II.字符以URL编码方式编码。

```
// 转换过程: {a: 1, b: 2} -> a=1&b=2 -> 如下(最终形式)
"a%3D1%26b%3D2"
```

2)`Content-Type —— multipart/form-data`

请求头中的`Content-Type`字段会包含`boundary`，且`boundary`的值有浏览器默认指定。例:`Content-Type: multipart/form-data;boundary=----WebkitFormBoundaryRRJKeWfHPGrS4LKe`。

数据会分为多个部分，每两个部分之间通过分隔符来分隔，每部分表述均有`HTTP`头部描述子包体，如`Content-Type`，在最后的分隔符会加上--表示结束。

```
Content-Disposition: form-data;name="data1";
Content-Type: text/plain
data1
----WebkitFormBoundaryRRJKeWfHPGrS4LKe
Content-Disposition: form-data;name="data2";
Content-Type: text/plain
data2
----WebkitFormBoundaryRRJKeWfHPGrS4LKe--
```

响应`Body`部分：

1）由已知长度的单个文件组成。该类型`body`由两个`header`定义：`Content-Type`和`Content-Length`

2）由未知长度的单个文件组成，通过将`Transfer-Encoding`设置为`chunked`来使用`chunks`编码。

关于`Content-Length`在下面`HTTP 1.0`中会提到，这个是`HTTP 1.0`中新增的非常重要的头部。

### 方法

安全方法：`HTTP`定义了一组被称为安全方法的方法。`GET`方法和`HEAD`方法都被认为是安全的，这意味着`GET`方法和`HEAD`方法都不会产生什么动作 ——`HTTP`请求不会再服务端产生什么结果，但这并不意味着什么动作都没发生，其实这更多的是`web`开发者决定的

- `GET`：请求服务器发送某个资源
- `HEAD`：跟`GET`方法类似，但服务器在响应中只返回了首部。不会返回实体的主体部分。
- `PUT`：向服务器中写入文档。语义：用请求的主体部分来创建一个由所请求的`URL`命名的新文档
- `POST`：用来向服务器中输入数据的。通常我们提交表单数据给服务器。【`POST`用于向服务器发送数据，`PUT`方法用于向服务器上的资源（例如文件）中存储数据】
- `TRACE`：主要用于诊断。实现沿通向目标资源的路径的消息环回（`loop-back`）测试 ，提供了一种实用的`debug`机制。
- `OPTIONS`：请求`WEB`服务器告知其支持的各种功能。可以询问服务器支持哪些方法。或者针对某些特殊资源支持哪些方法。
- `DELETE`：请求服务器删除请求`URL`中指定的的资源

### GET 和 POST 的区别

首先要了解下副作用和幂等的概念，副作用指的是对服务器端资源做修改。幂等指发送`M`和`N`次请求（两者不相同且都大于 1），服务器上资源的状态一致。应用场景上，get是无副作用的，幂等的。post 主要是有副作用的，不幂等的情况

技术上有以下的区分：

- 缓存：`Get`请求能缓存，`Post`请求不能
- 安全：`Get`请求没有`Post`请求那么安全，因为请求都在`URL`中。且会被浏览器保存历史纪录。`POST`放在请求体中，更加安全
- 限制：`URL`有长度限制，会干预`Get`请求，这个是浏览器决定的
- 编码：`GET`请求只能进行`URL`编码，只能接收`ASCII`字符，而`POST`没有限制。`POST`支持更多的编码类型，而且不对数据类型做限制
- 从`TCP`的角度，`GET`请求会把请求报文一次性发出去，而`POST`会分为两个`TCP`数据包，首先发`header`部分，如果服务器响应`100(continue)`， 然后发`body`部分。(火狐浏览器除外，它的`POST`请求只发一个`TCP`包)

### 状态码

- 100~199——信息性状态码

  101 Switching Protocols。在HTTP升级为WebSocket的时候，如果服务器同意变更，就会发送状态码 101。

- 200~299——成功状态码

  200 OK，表示从客户端发来的请求在服务器端被正确处理

  204 No content，表示请求成功，但响应报文不含实体的主体部分

  205 Reset Content，表示请求成功，但响应报文不含实体的主体部分，但是与 204 响应不同在于要求请求方重置内容

  206 Partial Content，进行范围请求

- 300~399——重定向状态码

  301 moved permanently，永久性重定向，表示资源已被分配了新的 URL

  302 found，临时性重定向，表示资源临时被分配了新的 URL

  303 see other，表示资源存在着另一个 URL，应使用 GET 方法获取资源

  304 not modified，表示服务器允许访问资源，但因发生请求未满足条件的情况

  307 temporary redirect，临时重定向，和302含义类似，但是期望客户端保持请求方法不变向新的地址发出请求

- 400~499——客户端错误状态码

  400 bad request，请求报文存在语法错误

  401 unauthorized，表示发送的请求需要有通过 HTTP 认证的认证信息

  403 forbidden，表示对请求资源的访问被服务器拒绝

  404 not found，表示在服务器上没有找到请求的资源

- 500~599——服务器错误状态码

  500 internal sever error，表示服务器端在执行请求时发生了错误

  501 Not Implemented，表示服务器不支持当前请求所需要的某个功能

  503 service unavailable，表明服务器暂时处于超负载或正在停机维护，无法处理请求

### 首部

```
HTTP Headers
```

1.通用首部（`General headers`）同时适用于请求和响应消息，但与最终消息主体中传输的数据无关的消息头。如`Date`

2.请求首部（`Request headers`）包含更多有关要获取的资源或客户端本身信息的消息头。如 User-Agent

3.响应首部（`Response headers`）包含有关响应的补充信息

4.实体首部（`Entity headers`）含有关实体主体的更多信息，比如主体长(`Content-Length`)度或其`MIME`类型。如`Accept-Ranges`

详细的`Header`见**HTTP Headers 集合**[2]

## **HTTP 的前世今生**

`HTTP（HyperText Transfer Protocol）`是万维网（`World Wide Web`）的基础协议。`Tim Berners-Lee`博士和他的团队在`1989-1991`年间创造出它。【HTTP、网络浏览器、服务器】

在 1991 年发布了`HTTP 0.9`版，在 1996 年发布 1.0 版，1997 年是 1.1 版，1.1 版也是到今天为止传输最广泛的版本。2015 年发布了 2.0 版，其极大的优化了`HTTP/1.1`的性能和安全性，而 2018 年发布的 3.0 版，继续优化`HTTP/2`，激进地使用`UDP`取代`TCP`协议，目前，`HTTP/3`在 2019 年 9 月 26 日 被`Chrome`，`Firefox`，和`Cloudflare`支持

![图片](https://mmbiz.qpic.cn/mmbiz_png/betIP9fVPicP9mOWh8f5hp8VpYXOEAAvNxKRINPCaFYRXHVXO7ibv21VJjwruiczoO8nQwxqptheQSkDhDUC9n7IQ/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

### HTTP 0.9

单行协议，请求由单行指令构成。以唯一可用的方法`GET`开头。后面跟的是目标资源的路径

```
GET /mypage.html
```

响应：只包括响应文档本身

```
<HTML>
这是一个非常简单的HTML页面
</HTML>
```

- 没有响应头，只传输`HTML`文件
- 没有状态码

### HTTP 1.0

**RFC 1945**[3]提出了`HTTP1.0`，**构建更好可拓展性**

- 协议版本信息会随着每个请求发送
- 响应状态码
- 引入了`HTTP`头的概念，无论是请求还是拓展，允许传输元数据。使协议变得灵活，更加具有拓展性
- `Content-Type`请求头，具备了传输除纯文本`HTML`文件以外其他类型文档的能力 。 在响应中，`Content-Type`标头告诉客户端实际返回的内容的内容类型

媒体类型是一种标准。用来表示文档、文件或者字节流的性质和格式。浏览器通常使用`MIME`（`Multipurpose Internet Mail Extensions`）类型来确定如何处理`URL`，因此`Web`服务器在响应头中配置正确的`MIME`类型会非常的重要。如果配置不正确，可能会导致网站无法正常的工作。`MIME`的组成结构非常简单；由类型与子类型两个字符串中间用'/'分隔而组成。

`HTTP`从`MIME type`取了一部分来标记报文`body`部分的数据类型，这些类型体现在`Content-Type`这个字段，当然这是针对于发送端而言，接收端想要收到特定类型的数据，也可以用`Accept`字段。

这两个字段的取值可以分为下面几类:

```
- text：text/html, text/plain, text/css 等
- image: image/gif, image/jpeg, image/png 等
- audio/video: audio/mpeg, video/mp4 等
- application: application/json, application/javascript, application/pdf, application/octet-stream
```

同时为了约定请求的数据和响应数据的压缩方式、支持语言、字符集等，还提出了以下的`Header`

1.压缩方式:发送端：`Content-Encoding`（服务端告知客户端，服务器对实体的主体部分的编码方式） 和 接收端：`Accept-Encoding`（用户代理支持的编码方式），值有 gzip: 当今最流行的压缩格式；deflate: 另外一种著名的压缩格式；br: 一种专门为 HTTP 发明的压缩算法

2.支持语言：`Content-Language`和`Accept-Language`（用户代理支持的自然语言集）

3.字符集：发送端：`Content-Type`中，以`charset`属性指定。接收端：`Accept-Charset`（用户代理支持的字符集）。

```
// 发送端
Content-Encoding: gzip
Content-Language: zh-CN, zh, en
Content-Type: text/html; charset=utf-8

// 接收端
Accept-Encoding: gzip
Accept-Language: zh-CN, zh, en
Accept-Charset: charset=utf-8
```

虽然  `HTTP1.0`在`HTTP 0.9`的基础上改进了很多，但还是存在这不少的缺点

`HTTP/1.0`版的主要缺点是，每个`TCP`连接只能发送一个请求。发送数据完毕，连接就关闭，如果还要请求其他资源，就必须再新建一个连接。`TCP`连接的新建成本很高，因为需要客户端和服务器三次握手，并且开始时发送速率较慢（`slow start`）。

`HTTP`最早期的模型，也是  `HTTP/1.0`的默认模型，是短连接。每一个`HTTP`请求都由它自己独立的连接完成；这意味着发起每一个`HTTP`请求之前都会有一次`TCP`握手，而且是连续不断的。

### HTTP 1.1

`HTTP/1.1`在1997年1月以 **RFC 2068**[4] 文件发布。

`HTTP 1.1`消除了大量歧义内容并引入了多项技术

- 连接可以复用。长连接：`connection: keep-alive`。`HTTP 1.1`支持长连接（`PersistentConnection`），在一个`TCP`连接上可以传送多个`HTTP`请求和响应，减少了建立和关闭连接的消耗和延迟，在`HTTP1.1`中默认开启`Connection：keep-alive`，一定程度上弥补了`HTTP1.0`每次请求都要创建连接的缺点。
- 增加了管道化技术（`HTTP Pipelinling`），允许在第一个应答被完全发送完成之前就发送第二个请求，以降低通信延迟。复用同一个`TCP`连接期间，即便是通过管道同时发送了多个请求，服务端也是按请求的顺序依次给出响应的；而客户端在未收到之前所发出所有请求的响应之前，将会阻塞后面的请求(排队等待)，这称为"队头堵塞"（`Head-of-line blocking`）。
- 支持响应分块，分块编码传输：`Transfer-Encoding: chunked``Content-length`声明本次响应的数据长度。`keep-alive`连接可以先后传送多个响应，因此用`Content-length`来区分数据包是属于哪一个响应。使用`Content-Length`字段的前提条件是，服务器发送响应之前，必须知道响应的数据长度。对于一些很耗时的动态操作来说，这意味着，服务器要等到所有操作完成，才能发送数据，显然这样的效率不高。更好的处理方法是，产生一块数据，就发送一块，采用"流模式"（`Stream`）取代"缓存模式"（`Buffer`）。因此，`HTTP 1.1`规定可以不使用`Content-Length`字段，而使用"分块传输编码"（`Chunked Transfer Encoding`）。只要请求或响应的头信息有`Transfer-Encoding: chunked`字段，就表明`body`将可能由数量未定的多个数据块组成。每个数据块之前会有一行包含一个 16 进制数值，表示这个块的长度；最后一个大小为 0 的块，就表示本次响应的数据发送完了。
- 引入额外的缓存控制机制。在`HTTP1.0`中主要使用`header`里的`If-Modified-Since`,`Expires`等来做为缓存判断的标准，`HTTP1.1`则引入了更多的缓存控制策略例如`Entity tag`,`If-None-Match`，`Cache-Control`等更多可供选择的缓存头来控制缓存策略。
- `Host`头。不同的域名配置同一个`IP`地址的服务器。`Host`是`HTTP 1.1`协议中新增的一个请求头，主要用来实现虚拟主机技术。

虚拟主机（`virtual hosting`）即共享主机（`shared web hosting`），可以利用虚拟技术把一台完整的服务器分成若干个主机，因此可以在单一主机上运行多个网站或服务。

举个栗子，有一台`ip`地址为`61.135.169.125`的服务器，在这台服务器上部署着谷歌、百度、淘宝的网站。为什么我们访问`https://www.google.com`时，看到的是`Google`的首页而不是百度或者淘宝的首页？原因就是`Host`请求头决定着访问哪个虚拟主机。

### HTTP 2.0

2015年，`HTTP2.0`面世。**rfc7540**[5]

- `HTTP/2`是二进制协议而不是文本协议。先来看几个概念：

- - 帧：客户端与服务器通过交换帧来通信，帧是基于这个新协议通信的最小单位。
  - 消息：是指逻辑上的 HTTP 消息，比如请求、响应等，由一或多个帧组成。
  - 流：流是连接中的一个虚拟信道，可以承载双向的消息；每个流都有一个唯一的整数标识符

`HTTP 2.0`中的帧将`HTTP/1.x`消息分成帧并嵌入到流 (`stream`) 中。数据帧和报头帧分离，这将允许报头压缩。将多个流组合，这是一个被称为多路复用 (`multiplexing`) 的过程，它允许更有效的底层`TCP`连接。

也就是说，流用来承载消息，消息又是有一个或多个帧组成。二进制传输的方式更加提升了传输性能。每个数据流都以消息的形式发送，而消息又由一个或多个帧组成。帧是流中的数据单位。

`HTTP`帧现在对`Web`开发人员是透明的。在`HTTP/2`中，这是一个在  `HTTP/1.1`和底层传输协议之间附加的步骤。`Web`开发人员不需要在其使用的`API`中做任何更改来利用`HTTP`帧；当浏览器和服务器都可用时，`HTTP/2`将被打开并使用。

![图片](https://mmbiz.qpic.cn/mmbiz_png/betIP9fVPicP9mOWh8f5hp8VpYXOEAAvNMmtnuGp9WEhPwEkyR5aDKe6TeyzJmMF3v5B6KiaY6Vumw2zVJicvl6ibg/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

- 这是一个复用协议。并行的请求能在同一个连接中处理，移除了`HTTP/1.x`中顺序和阻塞的约束。多路复用允许同时通过单一的`HTTP/2` 连接发起多重的请求-响应消息

之前我们提到，虽然`HTTP 1.1`有了长连接和管道化的技术，但是还是会存在 队头阻塞。而`HTTP 2.0`就解决了这个问题`HTTP/2`中新的二进制分帧层突破了这些限制，实现了完整的请求和响应复用：客户端和服务器可以将`HTTP`消息分解为互不依赖的帧，然后交错发送，最后再在另一端把它们重新组装起来。

![图片](https://mmbiz.qpic.cn/mmbiz_png/betIP9fVPicP9mOWh8f5hp8VpYXOEAAvN40MZ3yThKr1UPgOojc2ZTXcWUBor3icxCBc3biaZg8L5hMicJ9yYlA8pg/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

如上图所示，快照捕捉了同一个连接内并行的多个数据流。客户端正在向服务器传输一个`DATA`帧（数据流 5），与此同时，服务器正向客户端交错发送数据流 1 和数据流 3 的一系列帧。因此，一个连接上同时有三个并行数据流。

将 HTTP 消息分解为独立的帧，交错发送，然后在另一端重新组装是`HTTP 2`最重要的一项增强。事实上，这个机制会在整个网络技术栈中引发一系列连锁反应，从而带来巨大的性能提升，让我们可以：1.并行交错地发送多个请求，请求之间互不影响。2.并行交错地发送多个响应，响应之间互不干扰。3.使用一个连接并行发送多个请求和响应。4.消除不必要的延迟和提高现有网络容量的利用率，从而减少页面加载时间。5.不必再为绕过 HTTP/1.x 限制而做很多工作(比如精灵图)   ...

连接共享，即每一个`request`都是是用作连接共享机制的。一个`request`对应一个`id`，这样一个连接上可以有多个`request`，每个连接的`request`可以随机的混杂在一起，接收方可以根据`request`的`id`将`request`再归属到各自不同的服务端请求里面。

`HTTP 1.1`和`HTTP 2.0`的对比，可以参考这个**网站 demo 演示**[6]

`HTTP 1.1`演示如下：![图片](https://mmbiz.qpic.cn/mmbiz_png/betIP9fVPicP9mOWh8f5hp8VpYXOEAAvNb1ERhCvQicPiaSPL8D6bZnDblDnds2hEdBmQEIiae6DF0qK0w6HFzI11A/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

`HTTP2.0`演示如下：

![图片](https://mmbiz.qpic.cn/mmbiz_png/betIP9fVPicP9mOWh8f5hp8VpYXOEAAvNHic2cibZbaJWMO54Gguyds8rFtAlDvUVmwLYXyjNIjBVBiboicttoqUMibg/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

![图片](https://mmbiz.qpic.cn/mmbiz_png/betIP9fVPicP9mOWh8f5hp8VpYXOEAAvNtOp2wyzLZuQPgSAWMianTQpFZjL06GEzqy3mIy16VsSrxatPWPu9Neg/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

- 压缩了`headers`。`HTTP1.x`的`header`带有大量信息，而且每次都要重复发送，就造成了性能的损耗。为了减少此开销和提升性能，`HTTP/2`使用`HPACK`压缩格式压缩请求和响应标头元数据，这种格式采用两种简单但是强大的技术：这种格式支持通过静态霍夫曼代码对传输的标头字段进行编码，从而减小了各个传输的大小。这种格式要求客户端和服务器同时维护和更新一个包含之前见过的标头字段的索引列表（换句话说，它可以建立一个共享的压缩上下文），此列表随后会用作参考，对之前传输的值进行有效编码。

![图片](https://mmbiz.qpic.cn/mmbiz_png/betIP9fVPicP9mOWh8f5hp8VpYXOEAAvNicZbm31mVLbTvZTrT0XhlTKFTWgmDFhFe8Qicbneiby8DLEjL8oCe87Yg/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

- 服务端推送。其允许服务器在客户端缓存中填充数据，通过一个叫服务器推送的机制来提前请求。服务器向客户端推送资源无需客户端明确地请求，服务端可以提前给客户端推送必要的资源，这样可以减少请求延迟时间，例如服务端可以主动把`JS`和`CSS`文件推送给客户端，而不是等到`HTML`解析到资源时发送请求，这样可以减少延迟时间大致过程如下图所示：

![图片](https://mmbiz.qpic.cn/mmbiz_png/betIP9fVPicP9mOWh8f5hp8VpYXOEAAvN8GKs6xx8gDib3gxlsumSqtQr8u9xwOvCpT2TNOdKwiaWuqOHgfQ2bmTA/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

### 如何升级你的 HTTP 版本

使用`HTTP/1.1`和`HTTP/2`对于站点和应用来说是透明的。拥有一个最新的服务器和新点的浏览器进行交互就足够了。只有一小部分群体需要做出改变，而且随着陈旧的浏览器和服务器的更新，而不需`Web`开发者做什么，用的人自然就增加了

## **HTTPS**

`HTTPS`也是通过`HTTP`协议进行传输信息，但是采用了`TLS`协议进行了加密

### 对称加密和非对称加密

对称加密就是两边拥有相同的秘钥，两边都知道如何将密文加密解密。但是因为传输数据都是走的网络，如果将秘钥通过网络的方式传递的话，一旦秘钥被截获就没有加密的意义的

非对称加密

公钥大家都知道，可以用公钥加密数据。但解密数据必须使用私钥，私钥掌握在颁发公钥的一方。首先服务端将公钥发布出去，那么客户端是知道公钥的。然后客户端创建一个秘钥，并使用公钥加密，发送给服务端。服务端接收到密文以后通过私钥解密出正确的秘钥

### TLS 握手过程

`TLS`握手的过程采用的是非对称加密

- `Client Hello`: 客户端发送一个随机值(`Random1`)以及需要的协议和加密方式。
- `Server Hello`以及`Certificate`: 服务端收到客户端的随机值，自己也产生一个随机值(`Random2`)，并根据客户端需求的协议和加密方式来使用对应的方式，并且发送自己的证书（如果需要验证客户端证书需要说明）
- `Certificate Verify`: 客户端收到服务端的证书并验证是否有效，验证通过会再生成一个随机值(`Random3`)，通过服务端证书的公钥去加密这个随机值并发送给服务端，如果服务端需要验证客户端证书的话会附带证书
- `Server 生成 secret`: 服务端收到加密过的随机值并使用私钥解密获得第三个随机值(`Random3`)，这时候两端都拥有了三个随机值，可以通过这三个随机值按照之前约定的加密方式生成密钥，接下来的通信就可以通过该密钥来加密解密

## **HTTP 缓存**

### 强缓存

强缓存主要是由`Cache-control`和`Expires`两个`Header`决定的

`Expires`的值和头里面的`Date`属性的值来判断是否缓存还有效。`Expires`是`Web`服务器响应消息头字段，在响应`http`请求时告诉浏览器在过期时间前浏览器可以直接从浏览器缓存取数据，而无需再次请求。`Expires`的一个缺点就是，返回的到期时间是服务器端的时间，这是一个绝对的时间，这样存在一个问题，如果客户端的时间与服务器的时间相差很大（比如时钟不同步，或者跨时区），那么误差就很大。

`Cache-Control`指明当前资源的有效期，控制浏览器是否直接从浏览器缓存取数据还是重新发请求到服务器取数据。但是其设置的是一个相对时间。

指定过期时间：`max-age`是距离请求发起的时间的秒数，比如下面指的是距离发起请求 31536000S 内都可以命中强缓存

```
Cache-Control: max-age=31536000
```

表示没有缓存

```
Cache-Control: no-store
```

有缓存但要重新验证

```
Cache-Control: no-cache
```

私有和公共缓存

`public`表示响应可以被任何中间人（比如中间代理、`CDN`等缓存）   而`private`则表示该响应是专用于某单个用户的，中间人不能缓存此响应，该响应只能应用于浏览器私有缓存中。

```
Cache-Control: private
Cache-Control: public
```

验证方式：以下表示一旦资源过期（比如已经超过`max-age`），在成功向原始服务器验证之前，缓存不能用该资源响应后续请求

```
Cache-Control: must-revalidate
```

`Cache-control`优先级比`Expires`优先级高

以下是一个`Cache-Control`强缓存的过程：

- 首次请求，直接从 server 中获取。其中会设置`max-age=100`
- 第二次请求，`age=10`，小于 100，则命中`Cache`，直接返回
- 第三次请求，`age=110`，大于 110。强缓存失效，就需要再次请求`Server`

![图片](https://mmbiz.qpic.cn/mmbiz_png/betIP9fVPicP9mOWh8f5hp8VpYXOEAAvN7PwDYajlYQsy8kAzqtEQac5eu7NtGpAGvL4kCEROQuyU34ryhVvoNw/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

### 协商缓存

- `If-Modified-Since——Last-Modified`

`Last-Modified`表示本地文件最后修改日期，浏览器会在`request header`加上`If-Modified-Since`（上次返回的`Last-Modified`的值），询问服务器在该日期后资源是否有更新，有更新的话就会将新的资源发送回来

但是如果在本地打开缓存文件，就会造成`Last-Modified`被修改，所以在`HTTP / 1.1`出现了`ETag`

- `If-none-match——ETags`

`Etag`就像一个指纹，资源变化都会导致`ETag`变化，跟最后修改时间没有关系，`ETag`可以保证每一个资源是唯一的。`If-None-Match`的`header`会将上次返回的`Etag`发送给服务器，询问该资源的`Etag`是否有更新，有变动就会发送新的资源回来

```
If-none-match`、`ETags`优先级高于`If-Modified-Since、Last-Modified
```

第一次请求：

![图片](https://mmbiz.qpic.cn/mmbiz_png/betIP9fVPicP9mOWh8f5hp8VpYXOEAAvNKd9HCOXvg9evnZ8Via0v3j9cEeaauXuj2Zyic9uSds4diavSXarAVCO9g/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

第二次请求相同网页：

![图片](https://mmbiz.qpic.cn/mmbiz_png/betIP9fVPicP9mOWh8f5hp8VpYXOEAAvNDw5BPliaicNNulJDzMINsS4vIUeZuCJ7nGcCfOIXcC96ECk3V70Zmp7A/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

协商缓存，假如没有改动的话，返回 304 ，改动了返回 200 资源

- 200：强缓存`Expires/Cache-Control`失效时，返回新的资源文件
- 200`(from cache)`: 强缓`Expires/Cache-Control`两者都存在，未过期，`Cache-Control`优先`Expires`时，浏览器从本地获取资源成功
- 304`(Not Modified)`：协商缓存`Last-modified/Etag`没有过期时，服务端返回状态码304

现在的200`(from cache)`已经变成了`disk cache`(磁盘缓存)和`memory cache`(内存缓存)两种

![图片](https://mmbiz.qpic.cn/mmbiz_png/betIP9fVPicP9mOWh8f5hp8VpYXOEAAvNviccmhz1NGPpqCcyVG9NeibNSQOibSZfrkbYQZr6TU1iarHXicUSxeVSFaw/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

### revving 技术

上面提到`HTTP`缓存相关，但是很多有时候，我们希望上线之后需要更新线上资源。

`web`开发者发明了一种被`Steve Souders`称之为`revving`的技术。不频繁更新的文件会使用特定的命名方式：在`URL`后面（通常是文件名后面）会加上版本号。

弊端：更新了版本号，所有引用这些的资源的地方的版本号都要改变

`web`开发者们通常会采用自动化构建工具在实际工作中完成这些琐碎的工作。当低频更新的资源（`js/css`）变动了，只用在高频变动的资源文件（`html`）里做入口的改动。

## **Cookies**

`HTTP Cookie`（也叫`Web Cookie`或浏览器`Cookie`）是服务器发送到用户浏览器并保存在本地的一小块数据，它会在浏览器下次向同一服务器再发起请求时被携带并发送到服务器上。

### 创建 cookie

`Set-Cookie`响应头部和`Cookie`请求头部

```
Set-Cookie: <cookie名>=<cookie值>
```

### 会话期Cookie

会话期Cookie是最简单的`Cookie`：浏览器关闭之后它会被自动删除，也就是说它仅在会话期内有效。会话期`Cookie`不需要指定过期时间（`Expires`）或者有效期（`Max-Age`）。需要注意的是，有些浏览器提供了会话恢复功能，这种情况下即使关闭了浏览器，会话期`Cookie`也会被保留下来，就好像浏览器从来没有关闭一样

### 持久性Cookie

和关闭浏览器便失效的会话期`Cookie`不同，持久性`Cookie`可以指定一个特定的过期时间（`Expires`）或有效期（`Max-Age`）。

```
Set-Cookie: id=a3fWa; Expires=Wed, 21 Oct 2015 07:28:00 GMT;
```

### Cookie的Secure和HttpOnly 标记

标记为`Secure`的`Cookie`只应通过被`HTTPS`协议加密过的请求发送给服务端。

标记为`Secure`的`Cookie`只应通过被`HTTPS`协议加密过的请求发送给服务端。但即便设置了`Secure` 标记，敏感信息也不应该通过`Cookie`传输，因为`Cookie`有其固有的不安全性，`Secure`标记也无法提供确实的安全保障

通过`JavaScript`的`Document.cookie``API` 是无法访问带有`HttpOnly`标记的`cookie`。这么做是为了避免跨域脚本攻击（`XSS`）

```
Set-Cookie: id=a3fWa; Expires=Wed, 21 Oct 2015 07:28:00 GMT; Secure; HttpOnly
```

### Cookie的作用域

`Domain`和`Path`标识定义了`Cookie`的作用域：即`Cookie`应该发送给哪些`URL`。

`Domain`标识指定了哪些主机可以接受`Cookie`。如果不指定，默认为当前的主机（不包含子域名）。如果指定了`Domain`，则一般包含子域名。

例如，如果设置`Domain=mozilla.org`，则`Cookie`也包含在子域名中（如`developer.mozilla.org`）。

`Path`标识指定了主机下的哪些路径可以接受`Cookie`（该`URL`路径必须存在于请求`URL`中）。以字符 %x2F ("/") 作为路径分隔符，子路径也会被匹配。

例如，设置`Path=/docs`，则以下地址都会匹配：

```
/docs
/docs/Web/
/docs/Web/HTTP
```

### SameSite Cookies

`SameSite Cookie`允许服务器要求某个`cookie`在跨站请求时不会被发送，从而可以阻止跨站请求伪造攻击

- `None`浏览器会在同站请求、跨站请求下继续发送`cookies`，不区分大小写。【旧版本`chrome`默认`Chrome 80`版本之前】
- `Strict`浏览器将只在访问相同站点时发送`cookie`。
- `Lax`将会为一些跨站子请求保留，如图片加载或者`frames`的调用，但只有当用户从外部站点导航到`URL`时才会发送。如`link`链接

```
Set-Cookie: key=value; SameSite=Strict
None Strict Lax
```

在新版本的浏览器（`Chrome 80`之后）中，`SameSite`的默认属性是`SameSite=Lax`。换句话说，当`Cookie`没有设置`SameSite`属性时，将会视作`SameSite`属性被设置为`Lax`—— 这意味着`Cookies`将不会在当前用户使用时被自动发送。如果想要指定`Cookies`在同站、跨站请求都被发送，那么需要明确指定`SameSite`为`None`。因为这一点，我们需要好好排查旧系统是否明确指定`SameSite`，以及推荐新系统明确指定`SameSite`，以兼容新旧版本`Chrome`

更多`cookie`相关，可以查看我之前总结的一篇关于`cookie`的文章**前端须知的 Cookie 知识小结**[7]

## **HTTP访问控制（CORS）**

跨域资源共享（`CORS`）是一种机制，它使用额外的`HTTP`头告诉浏览器，让运行在一个`origin`(`domain`) 上的`web` 应用被准许访问来自不同源服务器上的指定的资源

![图片](https://mmbiz.qpic.cn/mmbiz_png/betIP9fVPicP9mOWh8f5hp8VpYXOEAAvNDFoJrQ5cVQM7F4SKEztgUIFoibfxk6CIqBa1gzuEDNcvsSwQSqanGSw/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

跨域资源共享标准新增了一组`HTTP`首部字段，允许服务器声明哪些源站通过浏览器有权限访问哪些资源。

### 简单请求

简单请求（不会触发`CORS`的预检请求）需要同时满足以下三点：

- 方法是`GET/HEAD/POST`之一
- `Content-Type`的值仅限`text/plain`、`multipart/form-data`、`application/x-www-form-urlencoded`三者之一
- `HTTP`头部不能超过以下字段：`Accept`、`Accept-Language`、`Content-Language``Content-Type`（需要注意额外的限制）`DPR`、`Downlink`、`Save-Data`、`Viewport-Width`、`Width`

以下为一个简单请求的请求报文以及响应报文

![图片](https://mmbiz.qpic.cn/mmbiz_png/betIP9fVPicP9mOWh8f5hp8VpYXOEAAvNxicTnyzNjev1XibjnRXJYd3ZO8ULKzBu0GPtVLBjeE1x0ccKw9WoJ7xA/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

简化以下：

![图片](https://mmbiz.qpic.cn/mmbiz_png/betIP9fVPicP9mOWh8f5hp8VpYXOEAAvNT4qibNDPb1c87QtWFIJf4mQWibnM7rtoBAzgDR5jOZicOO5zicPNbpUSuw/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

请求首部字段`Origin`表明该请求来源于`http://foo.example`

本例中，服务端返回的`Access-Control-Allow-Origin: *`表明，该资源可以被任意外域访问。如果服务端仅允许来自`http://foo.example`的访问，该首部字段的内容如下：

```
Access-Control-Allow-Origin: http://foo.example
```

`Access-Control-Allow-Origin`应当为 * 或者包含由`Origin`首部字段所指明的域名。

### 预检请求

规范要求，对那些可能对服务器数据产生副作用的`HTTP`请求方法。浏览器必须首先使用`OPTIONS`方法发起一个预检请求（`preflight request`），从而获知服务端是否允许该跨域请求。

服务器确认允许之后，才发起实际的`HTTP`请求。在预检请求的返回中，服务器端也可以通知客户端，是否需要携带身份凭证（包括`Cookies`和`HTTP`认证相关数据）

![图片](https://mmbiz.qpic.cn/mmbiz_png/betIP9fVPicP9mOWh8f5hp8VpYXOEAAvNyW8Ug3CAjia0LfZQIBkhH2k9tNNEqCVPswGtTHoSiaboOcnf5ib22YTpg/640?wx_fmt=png&wxfrom=5&wx_lazy=1&wx_co=1)

预检请求中同时携带了下面两个首部字段：

```
Access-Control-Request-Method: POST
Access-Control-Request-Headers: X-PINGOTHER, Content-Type
```

首部字段`Access-Control-Request-Method`告知服务器，实际请求将使用`POST`方法。首部字段`Access-Control-Request-Headers`告知服务器，实际请求将携带两个自定义请求首部字段：`X-PINGOTHER`与`Content-Type`。服务器据此决定，该实际请求是否被允许。

预检请求的响应中，包括了以下几个字段

```
Access-Control-Allow-Origin: http://foo.example
// 表明服务器允许客户端使用 POST, GET 和 OPTIONS 方法发起请求
Access-Control-Allow-Methods: POST, GET, OPTIONS
// 表明服务器允许请求中携带字段 X-PINGOTHER 与 Content-Type
Access-Control-Allow-Headers: X-PINGOTHER, Content-Type
// 表明该响应的有效时间为 86400 秒，也就是 24 小时。在有效时间内，浏览器无须为同一请求再次发起预检请求。
Access-Control-Max-Age: 86400
```

HTTP 请求和响应   一般而言，对于跨域`XMLHttpRequest`或`Fetch`请求，浏览器不会发送身份凭证信息。如果要发送凭证信息，需要设置`XMLHttpRequest`的某个特殊标志位。比如说`XMLHttpRequest`的`withCredentials`标志设置为`true`，则可以发送`cookie`到服务端。

对于附带身份凭证的请求，服务器不得设置`Access-Control-Allow-Origin`的值为“*”。这是因为请求的首部中携带了`Cookie`信息，如果`Access-Control-Allow-Origin`的值为“*”，请求将会失败。而将`Access-Control-Allow-Origin`的值设置为`http://foo.example`，则请求将成功执行。

`CORS`涉及到的请求和响应头如下：`HTTP`响应首部字段

- `Access-Control-Allow-Origin`允许访问该资源的外域`URI`。对于不需要携带身份凭证的请求，服务器可以指定该字段的值为通配符，表示允许来自所有域的请求。
- `Access-Control-Expose-Headers`头让服务器把允许浏览器访问的头放入白名单
- `Access-Control-Max-Age`头指定了`preflight`请求的结果能够被缓存多久
- `Access-Control-Allow-Credentials`头指定了当浏览器的`credentials`设置为`true`时是否允许浏览器读取`response`的内容。
- `Access-Control-Allow-Methods`首部字段用于预检请求的响应。其指明了实际请求所允许使用的`HTTP`方法。
- `Access-Control-Allow-Headers`首部字段用于预检请求的响应。其指明了实际请求中允许携带的首部字段。

`HTTP`请求首部字段

- `Origin`首部字段表明预检请求或实际请求的源站
- `Access-Control-Request-Method`首部字段用于预检请求。其作用是，将实际请求所使用的 HTTP 方法告诉服务器。
- `Access-Control-Request-Headers`首部字段用于预检请求。其作用是，将实际请求所携带的首部字段告诉服务器。

## **参考**

- **MDN**[8]
- **HTTP的发展**[9]
- **HTTP概述**[10]
- **HTTP/2 简介**[11]
- **缓存（二）——浏览器缓存机制：强缓存、协商缓存**[12]
- **（建议精读）HTTP灵魂之问，巩固你的 HTTP 知识体系**[13]

### 参考资料

[1]RFC 2616:*https://tools.ietf.org/html/rfc2616*[2]HTTP Headers 集合:*https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Headers*[3]RFC 1945:*https://tools.ietf.org/html/rfc1945*[4]RFC 2068:*https://tools.ietf.org/html/rfc2068*[5]rfc7540:*https://httpwg.org/specs/rfc7540.html*[6]网站 demo 演示:*https://http2.akamai.com/demo*[7]前端须知的 Cookie 知识小结:*https://juejin.im/post/6844903841909964813*[8]MDN:*https://developer.mozilla.org/zh-CN/docs/Web/HTTP*[9]HTTP的发展 :*https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Basics_of_HTTP/Evolution_of_HTTP*[10]HTTP概述:*https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Overview*[11]HTTP/2 简介:*https://developers.google.com/web/fundamentals/performance/http2?hl=zh-cn*[12]缓存（二）——浏览器缓存机制：强缓存、协商缓存:*https://github.com/amandakelake/blog/issues/41*[13]（建议精读）HTTP灵魂之问，巩固你的 HTTP 知识体系:*https://juejin.im/post/6844904100035821575#heading-62

# 参考资料

https://www.zhihu.com/question/41609070

https://mp.weixin.qq.com/s/iA8eXeTMxhFZcLxy8x8TTQ

https://mp.weixin.qq.com/s/Q_zBXKHQ8k4g73WzxLx6iA

https://mp.weixin.qq.com/s/0ck_po7hjU7NYa_8wMXAuQ

