# Paginator

Paginator to get html documents with JS support

[![Build][build_shield]][build_link]
[![Maintainable][maintainable_shield]][maintainable_link]
[![Coverage][coverage_shield]][coverage_link]
[![Issues][issues_shield]][issues_link]
[![Commit][commit_shield]][commit_link]
[![Dependencies][dependency_shield]][dependency_link]
[![License][license_shield]][license_link]
[![Central][central_shield]][central_link]
[![Tag][tag_shield]][tag_link]
[![Javadoc][javadoc_shield]][javadoc_link]
[![Size][size_shield]][size_shield]
![Label][label_shield]

[build_shield]: https://github.com/YunaBraska/paginator/workflows/JAVA_CI/badge.svg
[build_link]: https://github.com/YunaBraska/paginator/actions?query=workflow%3AJAVA_CI
[maintainable_shield]: https://img.shields.io/codeclimate/maintainability/YunaBraska/paginator?style=flat-square
[maintainable_link]: https://codeclimate.com/github/YunaBraska/paginator/maintainability
[coverage_shield]: https://img.shields.io/codeclimate/coverage/YunaBraska/paginator?style=flat-square
[coverage_link]: https://codeclimate.com/github/YunaBraska/paginator/test_coverage
[issues_shield]: https://img.shields.io/github/issues/YunaBraska/paginator?style=flat-square
[issues_link]: https://github.com/YunaBraska/paginator/commits/master
[commit_shield]: https://img.shields.io/github/last-commit/YunaBraska/paginator?style=flat-square
[commit_link]: https://github.com/YunaBraska/paginator/issues
[license_shield]: https://img.shields.io/github/license/YunaBraska/paginator?style=flat-square
[license_link]: https://github.com/YunaBraska/paginator/blob/master/LICENSE
[dependency_shield]: https://img.shields.io/librariesio/github/YunaBraska/paginator?style=flat-square
[dependency_link]: https://libraries.io/github/YunaBraska/paginator
[central_shield]: https://img.shields.io/maven-central/v/berlin.yuna/paginator?style=flat-square
[central_link]:https://search.maven.org/artifact/berlin.yuna/paginator
[tag_shield]: https://img.shields.io/github/v/tag/YunaBraska/paginator?style=flat-square
[tag_link]: https://github.com/YunaBraska/paginator/releases
[javadoc_shield]: https://javadoc.io/badge2/berlin.yuna/paginator/javadoc.svg?style=flat-square
[javadoc_link]: https://javadoc.io/doc/berlin.yuna/paginator
[size_shield]: https://img.shields.io/github/repo-size/YunaBraska/paginator?style=flat-square
[label_shield]: https://img.shields.io/badge/Yuna-QueenInside-blueviolet?style=flat-square
[gitter_shield]: https://img.shields.io/gitter/room/YunaBraska/paginator?style=flat-square
[gitter_link]: https://gitter.im/paginator/Lobby

### Requirements

* min java 8
* chrome installed on the machine

### Docker image
* [https://hub.docker.com/repository/docker/yuna88/paginator](https://hub.docker.com/repository/docker/yuna88/paginator)

# Configurations

* Please request any issues and wishes to
  GitHub [https://github.com/YunaBraska/paginator](https://github.com/YunaBraska/paginator)

| ENV VARIABLE | DEFAULT    | DESCRIPTION                |
|--------------|------------|----------------------------|
| SERVER_PORT  | 8089       | Server port                |
| N/A          | 10000      | HTML pages cache limit     |
| N/A          | 10800000ms | HTML pages cache life time |

### Endpoints

| METHOD  | URL               | REQUEST BODY                 | RETURN BODY                    | Description                   | 
| --------|-------------------|------------------------------|--------------------------------|-------------------------------|
| GET/PUT | /pages            | String url                   |                                | Get html page from url        |
| GET/PUT | /pages/elements   | url, Map\<queryId, cssQuery> | Map\<queryId, List\<Elements>> | Get specific html elements    |
| GET/PUT | /pages/statistics |                              | size, maxLifeTime, sizeLimit   | Get cache statistics          |
| GET/PUT | /pages            | url, content                 |                                | Manual add html page to cache |

### Examples

#### Get elements from HTML page

* Request: `GET http://localhost:8089/pages/elements`
* Body:

```json
{
  "url": "parse.example.com",
  "css_queries": {
    "form_text": "form p"
  }
}
```

* Response

```json
{
  "form_text": [
    {
      "tag": "P",
      "text": "Some example text here.",
      "selector": "html > body > div > form > p:nth-child(1)",
      "attributes": {
      },
      "children": [
      ]
    }
  ]
}
```

#### Cache custom html pages

* Request: `POST http://localhost:8089/pages`
* Body:

```json
{
  "url": "my.own.example.com",
  "content": "<!doctype html><html><head><title>Example Domain</title></head><body><div><h1>Example page</h1></div></body></html>"
}
```

* Request: `POST http://localhost:8089/pages`
* Body:

```json
{
  "url": "my.own.example.com",
  "content": "<!doctype html><html><head><title>Example Domain</title></head><body><div><h1>Example page</h1></div></body></html>"
}
```

### Docker build image example
* Create jar file: `mvn clean -Dmaven.test.skip=true package`
* Build local image `docker build -t paginator .`
* Docker image tag latest for repo: `docker tag "$(whoami)/paginator" SOME_REPO_PATH/paginator:latest;`
* Docker image push to repo:  `docker push SOME_REPO_PATH/paginator:latest`

### TODO

* Async page call implementation \[remove synchronised\]
* Endpoint to clear cache
* configurable cache limits
* Automate docker image build

```
    ////((((((((((((((((((((((((((((((* **         
    //////////////////////////////////* */(/.      
    //////////////////////////////////* */////*    
    //////////////////////////////////* *////////. 
    //////////////////////////////////*            
    ///////......................,////////////////.
    //////////////////////////////////////////////.
    ///////...............................,///////.
    ///////******************************/////////.
    //////////////////////////////////////////////.
    //////*.           PAGINATOR          ,///////.
    //////////////////////////////////////////////.
    **********************************************.
    **********************************************.
    ********,....*********************************.
    ********,    *********************************.
            .,***********,    ,*******************.
             ,,,,,,,,,,,,,    ,*,,,,,      .,,,,,,.
             ,,,,,,,,,    ,,,,,,,,,,,      .,,,,,,.
      ................    .......,,,.   .......... 
      ,,,,,,.                    ,,,.  .,,,.       
      ,,,,,,.       ....     ,,,.                  
                    ,,,.     ,,,.                  
                ....                               
                ....                               
                    ....                           
```
