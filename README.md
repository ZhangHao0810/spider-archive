# spider-archive

个人爬虫练习与历史项目归档仓库，收录过去不同阶段编写的新闻抓取、微博采集、图片下载等代码。

> 这里保存的不是一个正在持续打磨的统一系统，而是一段个人学习路径的代码记录。
>
> 从早期的 Python 单脚本，到后来的 Spring Boot 新闻采集项目，这些代码基本保留了当时的实现方式，也保留了那段时间里最真实的技术选择、练习痕迹和思路演变。

## 项目简介

这个仓库主要用于统一归档我过去写过的各类爬虫相关项目，覆盖 Java 与 Python 两种实现方式。相比重新包装成一个全新的项目，我更希望把这些旧代码作为个人作品档案保留下来，方便自己回顾，也方便在 GitHub 上集中展示。

适合把它理解成：

- 个人爬虫练习项目合集
- 旧项目归档仓库
- 学习轨迹与代码快照集合
- GitHub 展示用的长期占坑仓库

## 收录内容

### Java 项目

1. `projects/java/search-boot`
   - 最初版 Spring Boot 新闻采集项目
   - 包含定时抓取、MySQL 入库、关键词搜索、Redis 热搜排行

2. `projects/java/bmw-searchboot`
   - `search-boot` 的后续整理版
   - 结构更完整，代码形态更接近正式项目

### Python 项目

1. `projects/python/weibo-spider`
   - 微博用户信息与微博内容采集脚本
   - 单文件脚本，偏早期练手项目

2. `projects/python/forum-image-scraper`
   - 论坛帖子图片下载脚本
   - 来自早期 Python 爬虫练习

## 仓库特点

- 按语言分类整理，目录更清晰
- 保留旧项目原始结构，方便回顾历史代码
- 已移除各子项目独立 `.git`、`target/`、`.idea/` 等冗余内容
- 已将明显的历史本地密码改为占位值，降低公开仓库风险
- 以归档、展示、留档为主，不追求统一启动和统一工程化

## 目录结构

```text
spider-archive/
|-- projects/
|   |-- java/
|   |   |-- search-boot/
|   |   `-- bmw-searchboot/
|   `-- python/
|       |-- weibo-spider/
|       `-- forum-image-scraper/
|-- .gitignore
`-- README.md
```

## 说明

- 这是一个代码归档仓库，不是统一框架下的单体应用
- 部分目标站点、接口和代理配置已经过时，代码不保证现在仍可直接运行
- 如果后续还想继续整理，可以在这个仓库基础上继续补充更多历史项目


