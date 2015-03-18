try {
    var b, d, e, f, g, h, k;
    M = a.getData("Hm_lpvt_" + c.id) || 0;
    13 == M.length && (M = Math.round(M / 1E3));
    if (document.referrer) {
        var s = n;
        if (U(document.referrer) && U(document.location.href))s = l; else var H = ga(document.referrer), s = Z(H || "", document.location.hostname);
        d = s ? N - M > c.vdur ? 1 : 4 : 3
    } else d = N - M > c.vdur ? 1 : 4;
    b = 4 != d ? 1 : 0;
    if (h = a.getData("Hm_lvt_" + c.id)) {
        k = h.split(",");
        for (var v = k.length - 1; 0 <= v; v--)13 == k[v].length && (k[v] = "" + Math.round(k[v] / 1E3));
        for (; 2592E3 < N - k[0];)k.shift();
        g = 4 > k.length ? 2 : 3;
        for (1 === b && k.push(N); 4 <
        k.length;)k.shift();
        h = k.join(",");
        f = k[k.length - 1]
    } else h = N, f = "", g = 1;
    a.setData("Hm_lvt_" + c.id, h, c.age);
    a.setData("Hm_lpvt_" + c.id, N);
    e = N == a.getData("Hm_lpvt_" + c.id) ? "1" : "0";
    if (0 == c.nv && U(document.location.href) && ("" == document.referrer || U(document.referrer)))b = 0, d = 4;
    a.a.nv = b;
    a.a.st = d;
    a.a.cc = e;
    a.a.lt = f;
    a.a.lv = g;
    a.a.si = c.id;
    a.a.su = document.referrer;
    a.a.ds = ea;
    a.a.cl = fa + "-bit";
    a.a.ln = da;
    a.a.ja = ca ? 1 : 0;
    a.a.ck = ba ? 1 : 0;
    a.a.lo = "number" == typeof _bdhm_top ? 1 : 0;
    var z = a.a;
    b = "";
    if (navigator.plugins && navigator.mimeTypes.length) {
        var w =
            navigator.plugins["Shockwave Flash"];
        w && w.description && (b = w.description.replace(/^.*\s+(\S+)\s+\S+$/, "$1"))
    } else if (window.ActiveXObject)try {
        var K = new ActiveXObject("ShockwaveFlash.ShockwaveFlash");
        K && (b = K.GetVariable("$version")) && (b = b.replace(/^.*\s+(\d+),(\d+).*$/, "$1.$2"))
    } catch (Ga) {
    }
    z.fl = b;
    a.a.v = "1.0.68";
    a.a.cv = decodeURIComponent(a.getData("Hm_cv_" + c.id) || "");
    1 == a.a.nv && (a.a.tt = document.title || "");
    a.a.api = 0;
    var L = document.location.href;
    a.a.cm = D(L, "hmmd") || "";
    a.a.cp = D(L, "hmpl") || "";
    a.a.cw = D(L,
        "hmkw") || "";
    a.a.ci = D(L, "hmci") || "";
    a.a.cf = D(L, "hmsr") || "";
    0 == a.a.nv ? ta() : T(".*");
    if ("" != c.icon) {
        var A, B = c.icon.split("|"), V = "http://tongji.baidu.com/hm-web/welcome/ico?s=" + c.id, W = ("http:" == O ? "http://eiv" : "https://bs") + ".baidu.com" + B[0] + "." + B[1];
        switch (B[1]) {
            case "swf":
                var ha = B[2], ia = B[3], z = "s=" + V, w = "HolmesIcon" + N;
                A = '<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" id="' + w + '" width="' + ha + '" height="' + ia + '"><param name="movie" value="' + W + '" /><param name="flashvars" value="' + (z || "") + '" /><param name="allowscriptaccess" value="always" /><embed type="application/x-shockwave-flash" name="' +
                w + '" width="' + ha + '" height="' + ia + '" src="' + W + '" flashvars="' + (z || "") + '" allowscriptaccess="always" /></object>';
                break;
            case "gif":
                A = '<a href="' + V + '" target="_blank"><img border="0" src="' + W + '" width="' + B[2] + '" height="' + B[3] + '"></a>';
                break;
            default:
                A = '<a href="' + V + '" target="_blank">' + B[0] + "</a>"
        }
        document.write(A)
    }
    var X = document.location.hash.substring(1), za = RegExp(c.id), Aa = -1 < document.referrer.indexOf("baidu.com") ? l : n, ja = D(X, "jn"), Ba = /^heatlink$|^select$/.test(ja);
    if (X && za.test(X) && Aa && Ba) {
        var P = document.createElement("script");
        P.setAttribute("type", "text/javascript");
        P.setAttribute("charset", "utf-8");
        P.setAttribute("src", O + "//" + c.js + ja + ".js?" + a.a.rnd);
        var ka = document.getElementsByTagName("script")[0];
        ka.parentNode.insertBefore(P, ka)
    }
    a.k && a.k();
    a.j && a.j();
    if (c.rec || c.trust)a.a.nv ? (a.c = encodeURIComponent(document.referrer), window.sessionStorage ? u("Hm_from_" + c.id, a.c) : na("Hm_from_" + c.id, a.c, 864E5)) : a.c = (window.sessionStorage ? x("Hm_from_" + c.id) : oa("Hm_from_" + c.id)) || "";
    a.l && a.l();
    a.m && a.m();
    y(window, "unload", wa(a));
    var q = window._hmt;
    if (q && q.length)for (A = 0; A < q.length; A++) {
        var I = q[A];
        switch (I[0]) {
            case "_setAccount":
                1 < I.length && /^[0-9a-z]{32}$/.test(I[1]) && (a.a.api |= 1, window._bdhm_account = I[1]);
                break;
            case "_setAutoPageview":
                if (1 < I.length) {
                    var Y = I[1];
                    if (n === Y || l === Y)a.a.api |= 2, window._bdhm_autoPageview = Y
                }
        }
    }
    if ("undefined" === typeof window._bdhm_account || window._bdhm_account === c.id) {
        window._bdhm_account = c.id;
        var C = window._hmt;
        if (C && C.length)for (var q = 0, Ca = C.length; q < Ca; q++)E(C[q], "Array") && "_trackEvent" !== C[q][0] && "_trackRTEvent" !==
        C[q][0] ? a.i(C[q]) : a.d.push(C[q]);
        window._hmt = a.r
    }
    var la = a.o;
    "undefined" === typeof window._bdhm_autoPageview || window._bdhm_autoPageview === l ? (a.h = l, a.a.et = 0, a.a.ep = "", S(a, la)) : la.call(a)
} catch (ma) {
    a = [], a.push("si=" + c.id), a.push("n=" + encodeURIComponent(ma.name)), a.push("m=" + encodeURIComponent(ma.message)), a.push("r=" + encodeURIComponent(document.referrer)), J(O + "//hm.baidu.com/hm.gif?" + a.join("&"))
}