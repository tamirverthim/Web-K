# K-Browser

K-Browser is [FlyingSaucer](https://github.com/flyingsaucerproject/flyingsaucer)-based basic pure java browser and Swing browser component. In addition to FlyingSaucer features it supports:
* a `<script>` tag with limited set of JS features (see below)
* Not strict HTML. Parser replaced with modified [JSoup](https://github.com/jhy/jsoup) library)
* Embedded `<svg>` elements, implemented with [svgSalamander](https://github.com/blackears/svgSalamander).
  
Uses [JSoup](https://github.com/jhy/jsoup) as HTML parser and Nashorn JS engine as browser JavaScript runtime. Also provides inline SVG support, provided by [svgSalamander](https://github.com/blackears/svgSalamander).

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