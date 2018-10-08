# Web-K
[![Build Status](https://travis-ci.org/Earnix/Web-K.svg?branch=master)](https://travis-ci.org/Earnix/Web-K)

Web-K is [FlyingSaucer](https://github.com/flyingsaucerproject/flyingsaucer)-based pure java browser and Swing browser component. In addition to FlyingSaucer features it supports:
* a `<script>` tag with limited set of JS features (like Canvas). Nashosh JavaScript runtime is used. See features list below. 
* Not strict HTML. Standard Java XML parser and DOM replaced with modified [JSoup](https://github.com/jhy/jsoup) library). This provides support of HTML5 at parsing level.
* Embedded `<svg>` elements, implemented with [svgSalamander](https://github.com/blackears/svgSalamander).
FlyingSaucer PDF and SWT rendering was removed.

Main usecase - pure Java web view component. Not intended to be used as standalone web browser (at leas at this moment).

### List of supported JavaScript features
JavaScript APIs implementation based on [WHATWG DOM](https://dom.spec.whatwg.org/) and [WHATWG HTML](specification). It is currently very limited.
* HTMLCanvasElement
* CanvasRenderingContext2D (but not images-related functions)
* TextMetrics.width
* Document.getElementsByTagName
* Document.getElementsByClassName
* Document.createElement
* Document.getElementById
* Document.createTextNode
* Document.querySelector
* Document.querySelectorAll
* Document.body
* Document.childElementCount
* Element.tagName
* Element.className
* Element.id
* Element.classList
* Element.hasAttributes
* Element.attributes
* Element.getElementsByTagName
* Element.getElementsByClassName
* Element.previousElementSibling
* Element.nextElementSibling
* Element.children
* Element.style
* Element.innerHTML
* Element.outerHTML (read-only)
* Element.clientWidth
* Element.setAttribute
* Element.toggleAttribute
* Element.removeAttribute
* Element.hasAttribute
* Element.childElementCount
* Node.parentElement
* Node.childNodes
* Node.appendChild
* Node.addEventListener
* Node.removeEventListener
* Node.dispatchEvent
* NodeList
* CSSStyleAttribute
* Attr.name
* Attr.value
* Attr.nodeType
* Attr.nodeName
* Attr.parentElement
* Attr.nodeValue
* Attr.textContent
* Event.type
* HTMLCollection
* DOMTokenList.*
* Window.onload
* Window.document
* Window.console.log
* Window.console.error
* Window.setInterval
* Window.setTimeout
* Window.location
* Window.getComputedStyle

## license
GNU Lesser General Public License v3.0
