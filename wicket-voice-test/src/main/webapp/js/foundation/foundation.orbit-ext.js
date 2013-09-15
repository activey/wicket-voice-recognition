;(function ($, orbit) {
  'use strict';

  orbit.next = function($container) {
  	orbit._goto($container, 'next', function(){})
  };

  orbit.previous = function($container) {
    	orbit._goto($container, 'prev', function(){})
  };

}(Foundation.zj, Foundation.libs.orbit));
