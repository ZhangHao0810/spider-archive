# 确定网址 发起请求, 获取响应,筛选图片数据,保存
import requests
import re
import os


# 2020年2月14日16:32:23 有效


def mkdir(path):
    # 引入模块
    # import os

    # 去除首位空格
    path = path.strip()

    # 去除尾部 \ 符号
    path = path.rstrip("\\")

    # 去除 \，/，:，*，?，"，<，>，|  这些都是win系统不允许的命名字符.
    path = re.sub(r'[\\/:*?"<>|]', '', path)  # 前面是正则表达式，匹配多种字符（串）

    # 判断路径是否存在
    # 存在     True
    # 不存在   False
    isExists = os.path.exists(path)

    # 判断结果
    if not isExists:
        # 如果不存在则创建目录
        # 创建目录操作函数
        os.makedirs(path)

        print(path + ' 创建成功')
        return True
    else:
        # 如果目录存在则不创建，并提示目录已存在
        # print(path + ' 目录已存在')
        return False


# # 定义要创建的目录
#
# mkpath = "d:\\qttc\\web\\"
#
# # 调用函数
#
# mkdir(mkpath)


# url = "https://f.wonderfulday28.live/viewthread.php?tid=365311"
# "user-agent" 保存的是客户端的信息. pc iPhone  保存在一个自定义的字典中.
header = {
    "user-agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
                  "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36"
}

# 使用Get的原因是网站的head里面获取信息的方式是get
# 爬虫 - 模拟客户端进行网络请求
# 403 - 没有权限  200 - 成功     .text 文本数据
# response = requests.get(url, headers=header).text
# response.encoding = response.apparent_encoding
# print(response)


# 每一张图片都是对应一个url
# 数据提取/筛选 re - 正则表达式  规则根据网页图片的规则制定.
# .* 匹配任意多个字符. 从哪里查找  ?反贪婪  贪婪意味着有多少就抓多少.
# image_infos = re.findall('file="(.*?)"', response)
# print(image_infos)


for i in range(1, 2):
    page = i
    # 获得精品第i页.
    url_jing = "https://f.wonderfulday28.live/forumdisplay.php?fid=19&filter=digest&page=" + str(page)
    print("开始第" + str(page) + "精品页:" + url_jing)
    response1 = requests.get(url_jing, headers=header).text

    # 获得所有的单独页面链接列表.
    url_singles = re.findall('href="(.*)" style="font-weight: bold;color: #8F2A90">', response1)
    # 循环列表,获得单独页面链接
    for url_single in url_singles:
        # 获得单独页面的response
        resSingle = requests.get("https://f.wonderfulday28.live/" + url_single, headers=header)
        # 更改请求的编码格式
        resSingle.encoding = "utf-8"

        resSingleText = resSingle.text
        image_urls = re.findall('file="attachments/(.*?)"', resSingleText)
        name = re.findall('<h1>(.*)</h1>', resSingleText)

        # 去除 \，/，:，*，?，"，<，>，|  这些都是win系统不允许的命名字符.
        name[0] = re.sub(r'[\\/:*?"<>|]', '', name[0])  # 前面是正则表达式，匹配多种字符（串）

        print("开始帖子:" + name[0])

        isExists = os.path.exists(name[0] + "/")
        if isExists:
            print("已下载这个帖子,开始下一个帖子的下载")
            continue

        # 列表 image_urls
        for image_url in image_urls:

            # Wb  w写 b二进制
            # 创建文件夹
            mkdir(name[0])
            # 计数之后,便可以得到图片的阅读顺序了.
            count = 1
            downlodePath = name[0] + "/" + count + image_url
            count += 1

            print(image_url + " 正在下载....", end=' ')

            try:
                # content 拿到的是原始的内容. 图片 是二进制,拿不到,只能用content
                image_content = requests.get("https://f.wonderfulday28.live/attachments/" + image_url,
                                             headers=header, timeout=20).content
            except:
                print("下载超时,跳过.")
                continue

            # print(image_content)
            with open(downlodePath, "wb") as f:
                f.write(image_content)
            print("下载完成!!!!")
        print("结束帖子:" + name[0])
