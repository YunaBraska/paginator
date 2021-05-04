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
* min java 11
* chrome installed on the machine

### Endpoints
| METHOD | URL    | REQUEST BODY                     | RETURN BODY                            | Description            | 
| -------|-------------------|-----------------------|----------------------------------------|------------------------|
| GET    | /pages            | String url            |                                        | Get html page from url |
| GET    | /pages/statistics |                       | Response(size, maxLifeTime, sizeLimit) | Get cache statistics   |
| POST   | /pages            | Request(url, content) |                                        | Manual add html page to cache |

### TODO
* Async page call implementation \[remove synchronised\]
* Endpoint to clear cache
* configurable cache limits

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