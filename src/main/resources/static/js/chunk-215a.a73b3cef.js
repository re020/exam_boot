(window.webpackJsonp=window.webpackJsonp||[]).push([["chunk-215a"],{L6zk:function(e,t,a){"use strict";a.r(t);var i=a("oBwb"),n={data:function(){return{permission:this.$store.getters.auths,dialogFormVisible:!1,page:{currentPage:1,currentCount:10,totalCount:null,totalPage:null,sortName:"",sortOrder:"asc",params:{typeName:""},list:[]},type:{typeId:"",typeName:""},dialogTitle:"新增题型",currentPage4:4,loading:!0}},methods:{handleSizeChange:function(e){this.page.currentCount=e,this.list()},handleCurrentChange:function(e){this.page.currentPage=e,this.list()},sortHandler:function(e){this.page.sortName=e.prop,this.page.sortOrder=e.order,this.list()},save:function(){var e=this;i.a.save(this.type).then(function(t){200===t.code?(e.$message({message:t.msg,type:"success"}),e.dialogFormVisible=!1,e.list()):(e.dialogFormVisible=!1,e.$message.error("保存失败!"))})},list:function(){var e=this;this.$store.commit("SET_LOADING",!0),i.a.list(this.page).then(function(t){200==t.code&&(e.page=t.data)})},toUpdate:function(e){var t=this;i.a.get(e).then(function(e){200==e.code&&(t.type=e.data,t.dialogTitle="修改题型",t.dialogFormVisible=!0)})},toAdd:function(){this.type={typeId:"",typeName:""},this.dialogTitle="新增题型",this.dialogFormVisible=!0},toDelete:function(e){var t=this;this.$confirm("确定删除这条记录?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"error"}).then(function(){i.a.delete(e).then(function(e){200==e.code?t.$message({message:e.msg,type:"success"}):t.$message({message:e.msg,type:"error"}),t.list()})})},search:function(){this.list()}},created:function(){this.list()}},o=(a("O8il"),a("ZrdR")),r=Object(o.a)(n,function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("div",{staticClass:"table-header"},[a("el-form",{staticClass:"demo-form-inline",attrs:{inline:!0,model:e.page,size:"mini"}},[a("el-form-item",{attrs:{label:"搜索题型"}},[a("el-input",{attrs:{placeholder:"题型名",clearable:""},model:{value:e.page.params.typeName,callback:function(t){e.$set(e.page.params,"typeName",t)},expression:"page.params.typeName"}})],1),e._v(" "),a("el-form-item",[a("el-button",{attrs:{type:"primary",size:"mini"},on:{click:e.search}},[e._v("查询")])],1)],1),e._v(" "),a("el-divider"),e._v(" "),e.permission.indexOf("type:add")>=0?a("el-button",{attrs:{type:"primary",size:"mini"},on:{click:e.toAdd}},[e._v("添加")]):e._e()],1),e._v(" "),a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:this.$store.getters.loading,expression:"this.$store.getters.loading"}],staticStyle:{width:"100%"},attrs:{data:e.page.list,border:"",stripe:"",size:"mini"},on:{"sort-change":e.sortHandler}},[a("el-table-column",{attrs:{type:"index",width:"50"}}),e._v(" "),a("el-table-column",{attrs:{prop:"typeName",sortable:"custom",label:"题型名"}}),e._v(" "),a("el-table-column",{attrs:{label:"操作"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-dropdown",[a("el-button",{attrs:{type:"primary",size:"mini"}},[e._v("\n            操作\n            "),a("i",{staticClass:"el-icon-arrow-down el-icon--right"})]),e._v(" "),a("el-dropdown-menu",{attrs:{slot:"dropdown"},slot:"dropdown"},[a("el-dropdown-item",[e.permission.indexOf("type:update")>=0?a("el-button",{attrs:{size:"mini",type:"success"},on:{click:function(a){e.toUpdate(t.row.typeId)}}},[e._v("编辑")]):e._e()],1),e._v(" "),a("el-dropdown-item",[e.permission.indexOf("type:delete")>=0?a("el-button",{attrs:{size:"mini",type:"danger"},on:{click:function(a){e.toDelete(t.row.typeId)}}},[e._v("删除")]):e._e()],1)],1)],1)]}}])})],1),e._v(" "),a("div",{staticClass:"page-div"},[a("el-pagination",{attrs:{"current-page":e.page.currentPage,"page-sizes":[10,15,20,30],"page-size":e.page.currentCount,layout:"total, sizes, prev, pager, next, jumper",total:e.page.totalCount},on:{"size-change":e.handleSizeChange,"current-change":e.handleCurrentChange}})],1),e._v(" "),a("el-dialog",{attrs:{title:e.dialogTitle,visible:e.dialogFormVisible},on:{"update:visible":function(t){e.dialogFormVisible=t}}},[a("el-form",{ref:"form",attrs:{model:e.type,"label-width":"80px",size:"mini"}},[a("el-form-item",{attrs:{label:"题型名 :"}},[a("el-input",{attrs:{clearable:""},model:{value:e.type.typeName,callback:function(t){e.$set(e.type,"typeName",t)},expression:"type.typeName"}})],1),e._v(" "),a("el-form-item",[a("el-button",{attrs:{type:"primary"},on:{click:e.save}},[e._v("提交")]),e._v(" "),a("el-button",{on:{click:function(t){e.dialogFormVisible=!1}}},[e._v("取消")])],1)],1)],1)],1)},[],!1,null,"878d9656",null);r.options.__file="TypeList.vue";t.default=r.exports},O8il:function(e,t,a){"use strict";var i=a("rIEh");a.n(i).a},oBwb:function(e,t,a){"use strict";var i=a("t3Un");t.a={save:function(e){return""===e.typeId?Object(i.a)({url:"/type/add",method:"post",data:e}):Object(i.a)({url:"/type/update",method:"put",data:e})},list:function(e){return Object(i.a)({url:"/type/list",method:"post",data:e})},get:function(e){return Object(i.a)({url:"/type/get/"+e,method:"get"})},delete:function(e){return Object(i.a)({url:"/type/delete/"+e,method:"delete"})},update:function(e){return Object(i.a)({url:"/type/update",method:"put",data:e})},all:function(){return Object(i.a)({url:"/type/all",method:"get"})},allByKnowIds:function(e){return Object(i.a)({url:"/type/all",method:"post",data:e})},allByKnow:function(e){return Object(i.a)({url:"/type/all/"+e,method:"get"})}}},rIEh:function(e,t,a){}}]);