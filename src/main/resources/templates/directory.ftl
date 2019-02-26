<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Files within ${path}</title>

    <style>
        body {
            background: #fff;
            margin: 0;
            padding: 30px;
            -webkit-font-smoothing: antialiased;
            font-family: Menlo, Consolas, monospace;
        }

        main {
            max-width: 920px;
        }

        header {
            display: flex;
            justify-content: space-between;
        }

        a {
            color: #1A00F2;
            text-decoration: none;
        }

        h1 {
            font-size: 18px;
            font-weight: 500;
            margin-top: 0;
            color: #000;
            font-family: -apple-system, Helvetica;
            display: flex;
        }

        h1 a {
            color: inherit;
            font-weight: bold;
            border-bottom: 1px dashed transparent;
        }

        h1 a:hover {
            color: #7d7d7d;
        }

        h1 i {
            font-style: normal;
        }

        ul {
            margin: 0;
            padding: 20px 0 0 0;
        }

        ul.single-column {
            flex-direction: column;
        }

        ul li {
            list-style: none;
            padding: 10px 0;
            font-size: 14px;
            display: flex;
            justify-content: space-between;
        }

        ul li i {
            color: #9B9B9B;
            font-size: 11px;
            display: block;
            font-style: normal;
            white-space: nowrap;
            padding-left: 15px;
        }

        ul a {
            color: #1A00F2;
            white-space: nowrap;
            overflow: hidden;
            display: block;
            text-overflow: ellipsis;
        }

        /* file-icon – svg inlined here, but it should also be possible to separate out. */
        ul a::before {
            content: url("data:image/svg+xml; utf8, <svg xmlns='http://www.w3.org/2000/svg' width='16' height='16' viewBox='0 0 64 64'><g fill='transparent' stroke='currentColor' stroke-miterlimit='10'><path stroke-width='4' d='M50.46 56H13.54V8h22.31a4.38 4.38 0 0 1 3.1 1.28l10.23 10.24a4.38 4.38 0 0 1 1.28 3.1z'/><path stroke-width='2' d='M35.29 8.31v14.72h14.06'/></g></svg>");
            display: inline-block;
            vertical-align: middle;
            margin-right: 10px;
        }

        ul a:hover {
            color: #000;
        }

        ul a[class=''] + i {
            display: none;
        }

        /* folder-icon */
        ul a[class='']::before {
            content: url("data:image/svg+xml; utf8, <svg xmlns='http://www.w3.org/2000/svg' width='16' height='16' viewBox='0 0 64 64'><path fill='transparent' stroke='currentColor' stroke-width='4' stroke-miterlimit='10' d='M56 53.71H8.17L8 21.06a2.13 2.13 0 0 1 2.13-2.13h2.33l2.13-4.28A4.78 4.78 0 0 1 18.87 12h9.65a4.78 4.78 0 0 1 4.28 2.65l2.13 4.28h17.36a3.55 3.55 0 0 1 3.55 3.55z'/></svg>");
        }

        /* image-icon */
        ul a[class='gif']::before,
        ul a[class='jpg']::before,
        ul a[class='png']::before,
        ul a[class='svg']::before {
            content: url("data:image/svg+xml; utf8, <svg width='16' height='16' viewBox='0 0 80 80' xmlns='http://www.w3.org/2000/svg' fill='none' stroke='currentColor' stroke-width='5' stroke-linecap='round' stroke-linejoin='round'><rect x='6' y='6' width='68' height='68' rx='5' ry='5'/><circle cx='24' cy='24' r='8'/><path d='M73 49L59 34 37 52M53 72L27 42 7 58'/></svg>");
            width: 16px;
        }

        @media (min-width: 768px) {
            #toggle {
                display: inline-block;
            }

            ul {
                display: flex;
                flex-wrap: wrap;
            }

            ul li {
                width: 230px;
                padding-right: 20px;
            }

            ul.single-column li {
                width: auto;
            }
        }

        @media (min-width: 992px) {
            body {
                padding: 45px;
            }

            h1 {
                font-size: 15px;
            }

            ul li {
                font-size: 13px;
                box-sizing: border-box;
                justify-content: flex-start;
            }

            ul li:hover i {
                opacity: 1;
            }

            ul li i {
                font-size: 10px;
                opacity: 0;
                margin-left: 10px;
                margin-top: 3px;
                padding-left: 0;
            }
        }
    </style>
</head>

<body>
<main>
    <header>
        <h1>
            <i>Index of&nbsp; ${path}</i>
        </h1>
    </header>

    <ul id="files">
        <#list files as file>
        <li>
            <a href="${path}${file.name}" title="" class="${file.ext}">${file.name}</a>
        </li>
        </#list>
    </ul>
</main>
</body>
</html>